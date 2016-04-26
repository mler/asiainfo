package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0025Request;
import com.bdx.rainbow.mapp.model.rsp.SK0025Response;
import com.bdx.rainbow.service.mapp.ISK0025Service;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 删除台账
 * Created by core on 15/12/14.
 */
@Service("sk0025")
@Action(bizcode="sk0025", version = "1.0", usercheck=false, ipcheck=false)
public class SK0025Action extends AbstractMappAction<SK0025Request,SK0025Response> {

    @Autowired
    private ISK0025Service sk0025Service;

    @Override
    public void doAction(SK0025Request request, SK0025Response response) throws Exception {
        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if(null != sessionDetail) {
            sk0025Service.deleteLedgerById(request.getLedgerType(), request.getId());

        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
        }
    }
}
