package com.bdx.rainbow.service.ydzf.common.impl;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.urs.dubbo.IDubUserService;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.ExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.core.common.YDZFVfsSetting;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseInfoVO;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;

@Service("ydzfCommonService")
public class YDZFCommonService extends BaseService implements
		IYDZFCommonService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFCommonService.class);
	@Autowired
	protected IEnterpriseDubboService enterpriseService;
	@Autowired
	private IFileDubboService fileDubboService;
	@Autowired
	private YDZFVfsSetting ydzfVfsSetting;

    @Autowired
    private IDubUserService dubUserService;

	@Override
	public Map<String, Object> getEnterpriseDetailByIdDubbo(Integer enterpriseId)
			throws BusinessException, SystemException {
		if (enterpriseId == null || enterpriseId <= 0) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		try {
			return enterpriseService.getEnterpriseDetailById(enterpriseId);
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}
	}

	@Override
	public String getEnterpriseNameByIdDubbo(Integer enterpriseId)
			throws BusinessException, SystemException {
		if (enterpriseId == null || enterpriseId <= 0) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		Map<String, Object> map = this
				.getEnterpriseDetailByIdDubbo(enterpriseId);
		DubboEnterpriseInfo info = (DubboEnterpriseInfo) map.get("info");
		if(info==null||info.getEnterpriseName()==null)
		{
			return "";
		}
		return info.getEnterpriseName();

	}

	@Override
	public String uploadFileDubbo(byte[] contents,
			String extName, String fileName, Integer userId, boolean isTemp,
			boolean needValidate) throws Exception {
		if (contents == null || userId == null || userId <= 0) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		try {
			if (StringUtils.isBlank(fileName) && userId != null) {
				fileName = userId.toString() + "_" + System.currentTimeMillis()
						+ "_" + Math.random();
			}
			return fileDubboService.uploadFile(ydzfVfsSetting.getServerName(),
					contents, extName, fileName, userId, isTemp, needValidate);
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}

	}

	@Override
	public String uploadImgCommonDubbo(byte[] contents, String fileName,
			Integer userId) throws Exception {
		if (contents == null || userId == null || userId <= 0) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		return this.uploadFileDubbo(contents,
				"jpg", fileName, userId, false, true);

	}

	@Override
	public String getFileHttpUrlDubbo(String fileId) throws Exception {
		if (StringUtils.isBlank(fileId)) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		try {
			return fileDubboService.getHttpUrl(fileId,
					ydzfVfsSetting.getServerName());
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}

	}

	@Override
	public Map<String, String> getFileHttpUrlsDubbo(List<String> fileIdList)
			throws Exception {
		if (CollectionUtils.isEmpty(fileIdList)) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		try {
			return fileDubboService.getHttpUrls(fileIdList,
					ydzfVfsSetting.getServerName());
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}
	}

	@Override
	public void removeFileDubbo(String fileId, Integer userId) throws Exception {
		if (StringUtils.isBlank(fileId) || userId == null || userId <= 0) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		try {
		fileDubboService.removeFile(fileId, userId,
				ydzfVfsSetting.getServerName());
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}

	}

	@Override
	public void removeFilesDubbo(List<String> fileIdList, Integer userId)
			throws Exception {
		if (fileIdList == null || userId == null || userId <= 0) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		for (String fileId : fileIdList) {
			this.removeFileDubbo(fileId, userId);
		}

	}

	@Override
	public YdzfEnterpriseInfoVO getEnterpriseInfoByIdDubbo(Integer enterpriseId)
			throws BusinessException, SystemException {
		if (enterpriseId == null || enterpriseId <= 0) {
			throw new SystemException(new DefaultExceptionCode("参数为空"));
		}
		Map<String, Object> map = this
				.getEnterpriseDetailByIdDubbo(enterpriseId);
		DubboEnterpriseInfo info = (DubboEnterpriseInfo) map.get("info");
		if(info==null||info.getEnterpriseName()==null)
		{
			return null;
		}
		else
		{
			YdzfEnterpriseInfoVO vo= new YdzfEnterpriseInfoVO();
			vo.setManageEnterpriseId(info.getEnterpriseId());
			vo.setManageEnterpriseName(info.getEnterpriseName());
			vo.setLegalPerson(info.getLegalPerson());
			vo.setLegalPersonCert(info.getLegalPersonCert());
			vo.setLegalPersonPhone(info.getLegalPersonPhone());
			vo.setRegiAddress(info.getRegiAddress());
            vo.setBusinessType(info.getBusinessType());
			return vo;
		}
	}
    @Override
    public List<DubboEnterpriseInfo> queryEnterpriseInfos(DubboEnterpriseInfo condition) {
        try {
            Map<String, Object> resultMap = enterpriseService.getEnterpriseInfos(condition,-1,-1);
            List<DubboEnterpriseInfo> list=(List<DubboEnterpriseInfo>)resultMap.get("list");
            return list;
        } catch (Exception e) {
            log.error("调用dubbo企业接口错误", e);
        }
        return null;
    }

    @Override
    public List<SysUser> getUsers(SysUser obj, Integer start, Integer count, SysUserInfo logon) {
        try {
            return dubUserService.getUsers(obj,start,count,logon);
        } catch (Exception e) {
            log.error("调用dubbo用户接口错误", e);
        }
        return null;
    }

}
