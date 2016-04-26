package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * Created by core on 15/11/5.
 * 台帐详情
 */
public class SK0023Request extends BDXBody {
    private String id;
    private String ledgerType;//台帐类型

    public String getLedgerType() {
        return ledgerType;
    }

    public void setLedgerType(String ledgerType) {
        this.ledgerType = ledgerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
