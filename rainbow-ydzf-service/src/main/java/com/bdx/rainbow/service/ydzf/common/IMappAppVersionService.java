package com.bdx.rainbow.service.ydzf.common;

import com.bdx.rainbow.entity.ydzf.MappAppVersion;


public interface IMappAppVersionService {
	/**
     * 查询app版本
     * @param itemkey
     * @return
     * @throws Exception
     */
	public MappAppVersion getAppVersion(String itemkey) throws Exception;

}
