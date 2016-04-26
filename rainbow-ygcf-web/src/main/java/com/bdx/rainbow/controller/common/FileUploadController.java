package com.bdx.rainbow.controller.common;

import com.bdx.rainbow.service.common.IFileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传
 * Created by fusj on 16/1/22.
 */
@Controller
@RequestMapping("fileUpload")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * 文件上传
     * @param file
     * @param request
     * @param response
     */
    @RequestMapping
    public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");

            String path = fileUploadService.fileUpload(file.getInputStream());

            response.getWriter().write(path);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
