package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0018Request;
import com.bdx.rainbow.mapp.model.rsp.SK0018Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bdx.rainbow.service.mapp.IBasicService;
import com.bdx.rainbow.urs.dubbo.IDubDicService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.ParamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by core on 15/10/20.
 * 字典接口
 */
@Service("sk0018")
@Action(bizcode="sk0018", version = "1.0", usercheck=false, ipcheck=false)
public class SK0018Action extends AbstractMappAction<SK0018Request,SK0018Response> {

    @Autowired
    private IBasicService basicService;

    @Override
    public void doAction(SK0018Request request, SK0018Response response) throws Exception {

        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if(null != sessionDetail) {

            Map<String, Map<String,String>> dics = new HashMap<>();

            dics.put("CYJJXZ", basicService.findCacheByKey("CYJJXZ"));
            dics.put("ZJLX", basicService.findCacheByKey("ZJLX")) ;
            dics.put("SPZW", basicService.findCacheByKey("SPZW")) ;
            dics.put("SPZC", basicService.findCacheByKey("SPZC")) ;
            dics.put("LEDGER_TYPE", basicService.findCacheByKey("LEDGER_TYPE")) ;
            dics.put("AUDITFLOW", basicService.findCacheByKey("AUDITFLOW")) ;

            dics.put("ORG", basicService.getSysDeptTree(sessionDetail.getUserInfo()));

//        map.clear();
//        map.put("msgType","1,2,3,4,5,6");
//        this.response.setMessageIds(messageService.getMessageIds(map));

            HashMap<String,String> Honesty = new HashMap<>();
            Honesty.put("1","诚信等级A");
            Honesty.put("2","诚信等级B");
            Honesty.put("3","诚信等级C");
            dics.put("HONESTY",Honesty);

            response.setDics(dics);
        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
        }
    }
}
