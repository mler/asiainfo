package com.bdx.rainbow.service.ydzf.backlog;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfBackLogListInfoVO;
import com.bdx.rainbow.urs.entity.IUserInfo;

public interface IYDZFBacklogService {
	
	
	/**
	 * 查询执法状态纪录列表
	 * @param userInfo
	 * @param saveStatus（1，已保存，9，已提交）
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfBackLogListInfoVO> queryYdzfBackLogListInfo(IUserInfo userInfo,String saveStatus,int start,int limit) throws BusinessException,SystemException;

	/**
	 * 查询已提交数目
	 * @param userInfo
	 * @return
	 */
	public int querySubmitCount(IUserInfo userInfo);
	/**
	 * 查询已保存数目
	 * @param userInfo
	 * @return
	 */
	public int querySaveCount(IUserInfo userInfo);
	
	
	
}
