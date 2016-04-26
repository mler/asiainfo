package com.bdx.rainbow.service.common.impl;

import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.entity.vfs.FileUploadInfo;
import com.bdx.rainbow.mapper.vfs.FileUploadInfoMapper;
import com.bdx.rainbow.service.common.IFileUploadService;
import com.bdx.rainbow.service.vfs.IFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;

/**
 * 文件上传
 * Created by fusj on 16/1/22.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class FileUploadServiceImpl implements IFileUploadService {

    @Autowired
    private IFile fileService;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private FileUploadInfoMapper fileUploadInfoMapper;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @Override
    public String fileUpload(InputStream inputSteam) throws Exception {
        FileServerConfig config = fileService.getServerConfig(vfsSetting.getServerName());

        int userid = 1;

        String fileId = fileService.uploadFile(config, inputSteam, "xls", userid+"_"+System.currentTimeMillis()+"_"+Math.random(), userid, false, true);

        FileUploadInfo fileUploadInfo = fileUploadInfoMapper.selectByPrimaryKey(fileId);
        if(null == fileUploadInfo) {
            return "";
        }

        StringBuffer path = new StringBuffer();
        path.append(vfsSetting.getFileReadService());
        path.append(fileUploadInfo.getFilePath()).append(File.separatorChar).append(fileId).append(".").append(fileUploadInfo.getFileExt());

        return path.toString();
    }
}
