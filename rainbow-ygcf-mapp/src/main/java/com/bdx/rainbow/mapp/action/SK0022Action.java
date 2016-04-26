package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0022Request;
import com.bdx.rainbow.mapp.model.rsp.SK0022Response;
import com.bdx.rainbow.service.mapp.ISK0022Service;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 台账上传
 * Created by core on 15/11/5.
 */
@Service("sk0022")
@Action(bizcode="sk0022", version = "1.0", usercheck=false, ipcheck=false)
public class SK0022Action extends AbstractMappAction<SK0022Request,SK0022Response> {

    @Autowired
    private ISK0022Service sk0022Service;

    @Override
    public void doAction(SK0022Request request, SK0022Response response) throws Exception {

        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if (null != sessionDetail) {

            // 台账数据
            List<Map<String, String>> lists = request.getMap();

            sk0022Service.uploadLedger(request.getMap(), request.getTicket(), sessionDetail.getUserInfo());

        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
//            throw new BusinessException(new DefaultExceptionCode(ErrorCodeDefine.NO_USER_INFO, "登录超时!"));
        }
    }
}
