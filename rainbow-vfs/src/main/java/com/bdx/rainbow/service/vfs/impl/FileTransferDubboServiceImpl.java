package com.bdx.rainbow.service.vfs.impl;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.configuration.vfs.OpenOfficeSetting;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.vfs.FileTransferInfo;
import com.bdx.rainbow.entity.vfs.FileTransferInfoExample;
import com.bdx.rainbow.mapper.vfs.FileTransferInfoMapper;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;
import com.bdx.rainbow.vfs.dubbo.IFileTransferService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.collections.CollectionUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * office(word/excel/pdf)转换成PDF
 * Created by fusj on 16/4/7.
 */
@Service("fileTransferDubboService")
@Transactional(value="transactionManagerVfs", rollbackFor = {Exception.class})
public class FileTransferDubboServiceImpl implements IFileTransferService {

    private static final Logger logger = LoggerFactory.getLogger(FileTransferDubboServiceImpl.class);

    // 支持转换后缀
    private static final String[] SUPPORT_EXT = {"doc", "docx", "xls", "xlsx", "ppt", "pptx"};

    @Autowired
    private OpenOfficeSetting openOfficeSetting;

    @Autowired
    private IFileDubboService fileDubboService;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private FileTransferInfoMapper fileTransferInfoMapper;

    /**
     * office(word/excel/pdf)转换成PDF
     * @param sourcePath      文件http访问路径
     * @param reload   是否强制加载
     * @return         返回pdf文件访问路径
     * @throws Exception
     */
    @Override
    public String officeToPdf(String sourcePath, Boolean reload) throws Exception {

        FileTransferInfo fileTransferInfo = null;

        // 判断是否解析过
        FileTransferInfoExample example = new FileTransferInfoExample();
        example.createCriteria().andSourceUrlEqualTo(sourcePath);

        List<FileTransferInfo> fileList = fileTransferInfoMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(fileList)) {
            fileTransferInfo = fileList.get(0);

            if(!reload) {
                return fileDubboService.getHttpUrl(fileTransferInfo.getTargetFileId(), vfsSetting.getServerName());
            }
        }

        InputStream inputStream;

        File sourceFile = null;
        File targetFile = null;
        try {
            int lastIndexof = sourcePath.lastIndexOf(".");
            if(lastIndexof == -1) {
                logger.error("sourcePath:" + sourcePath + ",文件路径不正确");
                throw new Exception("文件类型不支持");
            }

            // 后缀
            String ext = sourcePath.substring(lastIndexof + 1);

            // 校验文件类型
            if(!checkFileType(ext)) {
                logger.error("sourcePath:" + sourcePath + ",文件类型不支持");
                throw new Exception("文件类型不支持");
            }

            // 跨服务器读取文件
            URL url = new URL(sourcePath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();

            // 原文件
            String sourceFilePath = openOfficeSetting.getTemp() + UUID.randomUUID().toString() + "." + ext;
            sourceFile = new File(sourceFilePath);
            if(!sourceFile.exists()) {
                sourceFile.createNewFile();
            }
            inputStreamToFile(inputStream, sourceFile);

            // 目标文件
            targetFile = new File(openOfficeSetting.getTemp() + UUID.randomUUID().toString() + ".pdf");
            if(!targetFile.exists()) {
                targetFile.createNewFile();
            }

            // 转换
            transBy3(sourceFile, targetFile);

            // 使用999用户上传
            // 上传源文件
            String sourceFileId = fileDubboService.uploadFile(vfsSetting.getServerName(), sourceFile, ext, 999, false, true);
            // 上传目标文件
            String targetFileId = fileDubboService.uploadFile(vfsSetting.getServerName(), targetFile, "pdf", 999, false, true);


            // 入库
            if(null == fileTransferInfo) {
                fileTransferInfo = new FileTransferInfo();

                fileTransferInfo.setCreateTime(DateUtil.getCurrent());
                fileTransferInfo.setSourceFileId(sourceFileId);
                fileTransferInfo.setSourceExt(ext);
                fileTransferInfo.setSourceUrl(sourcePath);
                fileTransferInfo.setTargetFileId(targetFileId);
                fileTransferInfo.setTargetExt("pdf");
                fileTransferInfo.setComplateTime(DateUtil.getCurrent());

                fileTransferInfoMapper.insertSelective(fileTransferInfo);
            }
            // 重新解析
            else {
                fileTransferInfo.setSourceFileId(sourceFileId);
                fileTransferInfo.setSourceExt(ext);
                fileTransferInfo.setSourceUrl(sourcePath);
                fileTransferInfo.setTargetFileId(targetFileId);
                fileTransferInfo.setTargetExt("pdf");
                fileTransferInfo.setComplateTime(DateUtil.getCurrent());

                fileTransferInfoMapper.updateByPrimaryKeySelective(fileTransferInfo);
            }

            String pdfUrl = fileDubboService.getHttpUrl(targetFileId, vfsSetting.getServerName());
            return pdfUrl;

        } catch (Exception ex) {
            logger.error("url:" + sourcePath + ",文件转换异常:" + ex.getMessage(), ex);
            throw new Exception("文件转换异常:" + ex.getMessage());
        } finally {
            // 删除临时文件
            if(null != sourceFile && sourceFile.exists()) {
                sourceFile.delete();
            }
            if(null != targetFile && targetFile.exists()) {
                targetFile.delete();
            }
        }
    }

    /**
     * 3.0版本转换
     * @param inputFile
     * @param outputFile
     * @throws Exception
     */
    private void transBy3(File inputFile, File outputFile) throws Exception {
        OfficeManager officeManager = null;
        try {
//        DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();

//        String officeHome = "/Applications/OpenOffice.app/Contents";
//        config.setOfficeHome(officeHome);

            ExternalOfficeManagerConfiguration config = new ExternalOfficeManagerConfiguration();
            config.setPortNumber(openOfficeSetting.getPort());

            officeManager = config.buildOfficeManager();
            officeManager.start();

            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);

            logger.debug("开始转换");
            long startTime = System.currentTimeMillis();
            converter.convert(inputFile, outputFile);
            logger.debug("转换结束，用时:" + (System.currentTimeMillis() - startTime) / 1000 + "s");

        } catch (Exception ex) {
            throw ex;
        } finally {
            officeManager.stop();
        }
    }

    /**
     * 查询转换结果
     * @param sourceUrl
     * @return
     * @throws Exception
     */
    @Override
    public String findFileBySourceUrl(String sourceUrl) throws Exception {
        return null;
    }

    /**
     * 文件类型校验
     * @param ext
     * @return
     */
    private boolean checkFileType(String ext) {
        for(String supportExt : SUPPORT_EXT) {
            if(supportExt.equals(ext)) {
                return true;
            }
        }

        return false;
    }

    /**
     * inputStream转file
     * @param ins
     * @param file
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception ex) {

        } finally {
            if(null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 远程主机执行linux命令
     * @param host
     * @param user
     * @param psw
     * @param port
     * @param command
     * @return
     */
    public static String exec(String host,String user,String psw,int port,String command){
        String result="";
        Session session =null;
        ChannelExec openChannel =null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(psw);
            session.connect();
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command);
            int exitStatus = openChannel.getExitStatus();
            System.out.println(exitStatus);
            openChannel.connect();
            InputStream in = openChannel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                result+= new String(buf.getBytes("gbk"),"UTF-8")+"    <br>\r\n";
            }
        } catch (JSchException | IOException e) {
            result+=e.getMessage();
        }finally{
            if(openChannel!=null&&!openChannel.isClosed()){
                openChannel.disconnect();
            }
            if(session!=null&&session.isConnected()){
                session.disconnect();
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        OfficeManager officeManager = null;
//        try {
//            String sourcePath = "/Users/fusj/Documents/asiainfo/15.saas/temp/1.xls";
//            String targetPath = "/Users/fusj/Documents/asiainfo/15.saas/temp/1.pdf";
//
//            File sourceFile = new File(sourcePath);
//            File targetFile = new File(targetPath);
//
////            DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
//            ExternalOfficeManagerConfiguration config = new ExternalOfficeManagerConfiguration();
//
////            String officeHome = "/Applications/LibreOffice.app/Contents/";
////            config.setOfficeHome(officeHome);
//            config.setPortNumber(8100);
//
//            officeManager = config.buildOfficeManager();
//            officeManager.start();
//
//            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
//
//            System.out.println("转换开始");
//            long startTime = System.currentTimeMillis();
//            converter.convert(sourceFile, targetFile);
//            System.out.println("转换完成，用时:" + (System.currentTimeMillis() - startTime) / 1000 + "s");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            if(null != officeManager) {
//                officeManager.stop();
//            }
//        }
//
//    }
}
