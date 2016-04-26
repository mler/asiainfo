package com.bdx.rainbow.service.mapp;

import java.util.List;
import java.util.Map;

/**
 * 0023台账明细详情
 * Created by fusj on 16/3/22.
 */
public interface ISK0023Service {
    /**
     * 台账明细详情,map中包含key＝ticket票证路径
     * @param ledgerType
     * @param id
     * @return
     */
    List<Map<String,String>> ledgerDetail(String ledgerType, String id) throws Exception;
}
