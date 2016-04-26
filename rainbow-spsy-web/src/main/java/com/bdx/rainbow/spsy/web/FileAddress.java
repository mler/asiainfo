package com.bdx.rainbow.spsy.web;

import com.bdx.rainbow.util.BeanFactory;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;

public class FileAddress {
	public static String getUrl(String vfsId){
		IFileDubboService fileDubboService = (IFileDubboService) BeanFactory.getObject("fileDubboService");
		String fileUrl = "";
		try {
			if(vfsId!=null && !vfsId.equals("")){
				fileUrl = fileDubboService.getHttpUrl(vfsId,"VFS_RAINBOW_FILE_SERVER_DUBBO");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileUrl;
	}
}
