package com.bdx.rainbow.controller.common;

import com.bdx.rainbow.common.mapper.ExcelInfoMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件下载
 * Created by fusj on 16/1/20.
 */
@Controller
@RequestMapping("fileDownload")
public class FileDownController {

    private static final String EXCEL_ROOT_PATH = "/excel/";

    /**
     * 文档下载
     */
    @RequestMapping("{mapper}")
    @ResponseBody
    public void download(@PathVariable("mapper")String mapperKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=uft-8");
        String filePath=request.getSession().getServletContext().getClassLoader().getResource(EXCEL_ROOT_PATH + ExcelInfoMapper.getDownloadFileName(mapperKey)).getFile();
        File file = new File(filePath);
        response.reset();
//        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(ExcelInfoMapper.getDownloadChineseName(mapperKey).getBytes("gbk"), "iso-8859-1"));  //转码之后下载的文件不会出现中文乱码
        response.addHeader("Content-Length", "" + file.length());
        try {
            //以流的形式下载文件
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
