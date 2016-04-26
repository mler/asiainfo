package com.bdx.rainbow.service.common;


import java.io.InputStream;

/**
 * 文件上传
 * Created by fusj on 16/1/22.
 */
public interface IFileUploadService {

    /**
     * 上传文件
     * @param inputSteam
     * @return
     */
    String fileUpload(InputStream inputSteam) throws Exception;
}
