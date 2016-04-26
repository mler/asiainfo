package com.bdx.rainbow.service.vfs;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;

@Service
@Transactional(value="transactionManagerVfs",rollbackFor=Exception.class)
public class FileDubboServic implements IFileDubboService {

	@Autowired
	private IFile fileService;

	@Override
	public String uploadFile(String fileServerName, byte[] contents,
			String extName, String fileName, Integer userid, boolean isTemp,
			boolean needValidate) throws Exception {
		
		if(contents == null)
			throw new Exception("文件内容contents为空");
		
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.uploadFile(fileServerConfig, contents, extName, fileName, userid, isTemp, needValidate);
	}

	@Override
	public String uploadFile(String fileServerName, InputStream is,
			String extName, String fileName, Integer userid, boolean isTemp,
			boolean needValidate) throws Exception {

		if(is == null)
			throw new Exception("文件内容 InputStream为空");
		
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.uploadFile(fileServerConfig, is, extName, fileName, userid, isTemp, needValidate);
	}

	@Override
	public String uploadFile(String fileServerName, InputStream is,
			String extName, String fileName, Integer userid) throws Exception {
		
		if(is == null)
			throw new Exception("文件内容 InputStream为空");
		
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.uploadFile(fileServerConfig, is, extName, fileName, userid);
	}

	public String uploadFile(String fileServerName, File file, String extName,
			Integer userid) throws Exception {
		
		if(file == null || file.exists() == false)
			throw new Exception("文件不存在");
		
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.uploadFile(fileServerConfig, file, extName, userid);
	
	}

	@Override
	public String uploadFile(String fileServerName, File file, String extName,
			Integer userid, boolean isTemp, boolean needValidate)
			throws Exception {
		
		if(file == null || file.exists() == false)
			throw new Exception("文件不存在");
		
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.uploadFile(fileServerConfig, file, extName, userid, isTemp, needValidate);
	}

	@Override
	public InputStream downloadFile(String fileid, Integer userid,
			String fileServerName) throws Exception {
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.downloadFile(fileid, userid, fileServerConfig);
	}

	@Override
	public void removeFile(String fileid, Integer userid, String fileServerName)
			throws Exception {
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		fileService.removeFile(fileid, userid, fileServerConfig);
	}

	@Override
	public String getHttpUrl(String fileid, String fileServerName)
			throws Exception {
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.getHttpUrl(fileid, fileServerConfig);
	}

	@Override
	public void tranFile(String fileid, Integer userid, String fileServerName)
			throws Exception {
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		fileService.tranFile(fileid, userid, fileServerConfig);
	}

	@Override
	public Map<String, String> getHttpUrls(List<String> fileids,
			String fileServerName) throws Exception {
		FileServerConfig fileServerConfig = fileService.getServerConfig(fileServerName);
		if(fileServerConfig == null)
			throw new Exception("["+fileServerName+"]文件服务配置不存在");
		
		return fileService.getHttpUrls(fileids, fileServerConfig);
	}

}
