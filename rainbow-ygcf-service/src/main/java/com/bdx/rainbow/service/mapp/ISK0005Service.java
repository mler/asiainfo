package com.bdx.rainbow.service.mapp;

import com.bdx.rainbow.urs.entity.IUserInfo;

import java.util.List;

/**
 * 0005接口，台账明细
 * Created by fusj on 16/3/22.
 */
public interface ISK0005Service {

    /**
     * 获取台账列表加明细
     * @param ledgerType
     * @param begTime
     * @param endTime
     * @param pageNumber
     * @param pageCount
     * @return
     */
    List legalList(String ledgerType, String begTime, String endTime, String pageNumber, String pageCount, IUserInfo sysUserInfo) throws Exception;
}
