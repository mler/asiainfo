package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 15/10/15.
 * 数据字典响应包
 */
public class SK0018Response extends BDXBody {

    private List<String> MessageIds;
    private Map<String, Map<String,String>> dics;

    public Map<String, Map<String,String>> getDics() {
        return dics;
    }

    public void setDics(Map<String, Map<String,String>> dics) {
        this.dics = dics;
    }

    public List<String> getMessageIds() {
        return MessageIds;
    }

    public void setMessageIds(List<String> messageIds) {
        MessageIds = messageIds;
    }
}
