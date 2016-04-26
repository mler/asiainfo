package com.bdx.rainbow.service.ydzf.common;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseInfoVO;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;

public interface IYDZFCommonService {

	public Map<String, Object> getEnterpriseDetailByIdDubbo(Integer enterpriseId)
			throws BusinessException, SystemException;

	public String getEnterpriseNameByIdDubbo(Integer enterpriseId)
			throws BusinessException, SystemException;
	
	
	public YdzfEnterpriseInfoVO getEnterpriseInfoByIdDubbo(Integer enterpriseId)
			throws BusinessException, SystemException;

	/**
	 * 
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
	public String uploadFileDubbo(byte[] contents, String extName,
			String fileName, Integer userId, boolean isTemp,
			boolean needValidate) throws Exception;

	/**
	 * 
	 * @param contents
	 * @param fileName
	 *            (如果为空，默认：
	 *            userId.toString()+"_"+System.currentTimeMillis()+"_"+
	 *            Math.random())
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public String uploadImgCommonDubbo(byte[] contents, String fileName,
			Integer userId) throws Exception;

	/**
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public String getFileHttpUrlDubbo(String fileId) throws Exception;

	/**
	 * 
	 * @param fileIdList
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFileHttpUrlsDubbo(List<String> fileIdList)
			throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param fileid
	 * @param userid
	 * @param fileServerName
	 * @throws Exception
	 */
	public void removeFileDubbo(String fileId, Integer userid) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param fileids
	 * @param userid
	 * @throws Exception
	 */
	public void removeFilesDubbo(List<String> fileIdList, Integer userid)
			throws Exception;
    public List<DubboEnterpriseInfo> queryEnterpriseInfos(DubboEnterpriseInfo condition);

    public List<SysUser> getUsers(SysUser obj, Integer start, Integer count,SysUserInfo logon);

}
