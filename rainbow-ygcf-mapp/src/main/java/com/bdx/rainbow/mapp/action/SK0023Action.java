package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0023Request;
import com.bdx.rainbow.mapp.model.rsp.SK0023Response;
import com.bdx.rainbow.service.mapp.ISK0023Service;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 台账明细详情
 * Created by core on 15/11/5.
 */
@Service("sk0023")
@Action(bizcode="sk0023", version = "1.0", usercheck=false, ipcheck=false)
public class SK0023Action extends AbstractMappAction<SK0023Request,SK0023Response> {

    @Autowired
    private ISK0023Service sk0023Service;

    @Override
    public void doAction(SK0023Request request, SK0023Response response) throws Exception {

        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if (null != sessionDetail) {
            List<Map<String, String>> maps = sk0023Service.ledgerDetail(request.getLedgerType(), request.getId());

            response.setLists(maps);

            if(maps.get(maps.size() - 1).containsKey("ticket")) {
                response.setTicket(maps.get(0).get("ticket"));
            }
            maps.remove(maps.size() - 1);

        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
//            throw new BusinessException(new DefaultExceptionCode(ErrorCodeDefine.NO_USER_INFO, "登录超时!"));
        }
    }
}
