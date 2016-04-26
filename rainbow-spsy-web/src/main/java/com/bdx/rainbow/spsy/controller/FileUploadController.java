package com.bdx.rainbow.spsy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;

@Controller
@RequestMapping("upload")
public class FileUploadController {
	@Autowired
	private IFileDubboService fileDubboService;
	
	@RequestMapping(value = { "imageUpload" }, method = RequestMethod.POST)
	@ResponseBody
	public void uploadImages(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		byte[] bytes = file.getBytes();
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
        int userId = userInfo.getUserId();
		try {
			String fileName = file.getOriginalFilename();
			String extName = FilenameUtils.getExtension(fileName);
			String fileId = fileDubboService.uploadFile("VFS_RAINBOW_FILE_SERVER_DUBBO", bytes, extName, fileName, userId, false, true);
			String url = fileDubboService.getHttpUrl(fileId, "VFS_RAINBOW_FILE_SERVER_DUBBO");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(fileId + ";" + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(value = { "delimage" }, method = RequestMethod.GET)
	public void removeImage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "vfsId") String vfsId) throws Exception{
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
        int userId = userInfo.getUserId();
        try{
        	fileDubboService.removeFile(vfsId, userId, "VFS_RAINBOW_FILE_SERVER_DUBBO");
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
}
