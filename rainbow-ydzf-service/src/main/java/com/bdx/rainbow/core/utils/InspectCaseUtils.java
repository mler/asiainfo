package com.bdx.rainbow.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.service.ydzf.inspect.impl.YDZFInspectCaseExecutePunishService;


/**
 * 稽查办案工具类
 * @author fox
 *
 */
public class InspectCaseUtils {
	private static final Logger log = LoggerFactory
			.getLogger(InspectCaseUtils.class);
	public  static boolean checkUpdate(TYdzfInspectCase ydzfInspectCase) throws BusinessException
	{
		if(ydzfInspectCase==null)
		{
			log.error("稽查案件不存在");
			throw new BusinessException(new DefaultExceptionCode("稽查案件不存在"));
		}
		if(ydzfInspectCase.getInspectCaseStatus().equals(YDZFConstants.INSPECTCASE.INSPECT_STATUS_FINISH))
		{
			log.error("已结案，不允许修改");
			throw new BusinessException(new DefaultExceptionCode("已结案，不允许修改"));
		}
		return true;
	}

}
