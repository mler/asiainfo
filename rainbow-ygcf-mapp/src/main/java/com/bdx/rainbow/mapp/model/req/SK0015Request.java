package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * Created by core on 15/10/16.
 * 监控请求
 */
public class SK0015Request extends BDXBody {
    private String enterpriseId;

    public SK0015Request() {
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

}
