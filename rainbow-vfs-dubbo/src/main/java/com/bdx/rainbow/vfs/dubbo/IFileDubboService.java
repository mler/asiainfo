package com.bdx.rainbow.vfs.dubbo;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IFileDubboService {
	
//	/**
//	 * 场景：用于外服务下载文件时，通过aip到woego内部下载文件后，上传回原应用
//	 * @param fileServerConfig
//	 * @param fileid 获取的文件ID
//	 * @param extName 扩展名
//	 * @param path 回传的路径
//	 * @param is
//	 * @return
//	 * @throws SystemException
//	 * @throws BusinessException
//	 */
//	public String downloadAndUploadFile(String fileServiceName, String fileid,
//			String extName, String path, InputStream is, String fileName)
//			throws Exception;
	
	/**
	 * 
	 * @param fileServerConfig
	 * @param contents
	 * @param extName
	 * @param fileName
	 * @param userInfo
	 * @param isTemp
	 * @param needValidate
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public String uploadFile(String fileServerName,
			byte[] contents, String extName, String fileName,
			Integer userid, boolean isTemp, boolean needValidate)
			throws Exception;

	/**
	 * 
	 * @param fileServerConfig
	 * @param is
	 *            外部送入流
	 * @param extName
	 *            文件扩展名
	 * @param fileName
	 *            文件名称
	 * @param userInfo
	 *            操作员信息
	 * @param isTemp
	 *            是否上传到临时文件夹
	 * @param needValidate
	 *            权限标示；false:无权限；true:有权限
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public String uploadFile(String fileServerName, InputStream is,
			String extName, String fileName, Integer userid,
			boolean isTemp, boolean needValidate) throws Exception;

	/**
	 * 把流上传到文件服务器，默认按无权限模式进行上传
	 * 
	 * @param fileServerConfig
	 * @param is
	 * @param extName
	 * @param fileName
	 * @param userInfo
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public String uploadFile(String fileServerName, InputStream is,
			String extName, String fileName, Integer userid)
			throws Exception;

//	/**
//	 * 把文件上传到文件服务器，默认按无权限模式进行上传
//	 * 
//	 * @param fileServerConfig
//	 *            文件服务器信息
//	 * @param file
//	 *            文件
//	 * @param extName
//	 *            扩展名
//	 * @param userInfo
//	 *            操作员信息
//	 * @return 返回fileid
//	 * @throws SystemException
//	 *             ,BusinessException
//	 */
//	public String uploadFile(String fileServerName, File file,
//			String extName, Integer userid) throws Exception;

	/**
	 * 把文件上传到文件服务器
	 * 
	 * @param fileServerConfig
	 * 
	 * @param file
	 *            文件
	 * @param extName
	 *            扩展名
	 * @param userInfo
	 *            操作员信息
	 * @param serverName
	 *            服务器名称
	 * @param isTemp
	 *            是否上传临时文件夹
	 * @param needValidate
	 *            权限标示；false:无权限；true:有权限
	 * @return 返回fileid
	 * @throws SystemException
	 *             ,BusinessException
	 */
	public String uploadFile(String fileServerName, File file,
			String extName, Integer userid, boolean isTemp,
			boolean needValidate) throws Exception;

	/**
	 * 从文件服务器下载文件
	 * 
	 * @param fileId
	 *            文件Id
	 * @param userInfo
	 *            操作员信息
	 * @return 返回流对象InputStream
	 * @throws SystemException
	 *             ,BusinessException
	 */
	public InputStream downloadFile(String fileid, Integer userid, String fileServerName)
			throws Exception;

	/**
	 * 从文件服务器删除文件
	 * 
	 * @param fileName
	 *            待删除文件id
	 * @param userInfo
	 *            操作员信息
	 * @throws SystemException
	 *             ,BusinessException
	 */
	public void removeFile(String fileid, Integer userid, String fileServerName)
			throws Exception;

	/**
	 * 根据传入fileid返回文件http访问地址；
	 * 
	 * @param serverName
	 *            服务器名称
	 * @param fileid
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public String getHttpUrl(String fileid, String fileServerName) throws Exception;

	/**
	 * 把文件从临时目录转移正式
	 * 
	 * @param fileid
	 * @param userInfo
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void tranFile(String fileid, Integer userid, String fileServerName)
			throws Exception;

	/**
	 * <p>
	 * 描述:批量获得文件访问路径
	 * </p>
	 * 
	 * @param fileids
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 * @author Zhengwj
	 * @Date 2014-3-7 上午09:11:24
	 */
	public Map<String, String> getHttpUrls(List<String> fileids, String fileServerName)
			throws Exception;
	
}
