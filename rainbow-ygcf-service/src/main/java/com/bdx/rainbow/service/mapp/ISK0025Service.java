package com.bdx.rainbow.service.mapp;

/**
 * 台账删除
 * Created by fusj on 16/3/28.
 */
public interface ISK0025Service {
    /**
     * 删除台账
     * @param ledgerType
     * @param id
     */
    void deleteLedgerById(String ledgerType, String id) throws Exception;
}
