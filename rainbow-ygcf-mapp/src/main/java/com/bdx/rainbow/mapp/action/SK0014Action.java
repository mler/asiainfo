package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0014Request;
import com.bdx.rainbow.mapp.model.rsp.SK0014Response;

import com.bdx.rainbow.service.mapp.IBasicService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by core on 15/10/19.
 * 更新企业信息
 *  暂时不需要，写了一半 TODO
 */
@Service("sk0014")
@Action(bizcode = "sk0014", version = "1.0",usercheck = false, ipcheck=false)
public class SK0014Action extends AbstractMappAction<SK0014Request,SK0014Response> {

    @Override
    public void doAction(SK0014Request request, SK0014Response response) throws Exception {

        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if(null != sessionDetail && null != sessionDetail.getCorpId()) {

            DubboEnterpriseInfo enterpriseInfo = new DubboEnterpriseInfo();

            if(StringUtil.isNotEmptyObject(request.getCompanyName())) {
                enterpriseInfo.setEnterpriseName(request.getCompanyName());
            }

            if(StringUtil.isNotEmptyObject(request.getEconomicNature())) {
                enterpriseInfo.setRegiCapital(request.getEconomicNature());
            }

            if(StringUtil.isNotEmptyObject(request.getLegalRepresentative())) {
                enterpriseInfo.setLegalPerson(request.getLegalRepresentative());
            }

            if(StringUtil.isNotEmptyObject(request.getLegalRepresentativePhone())) {
                enterpriseInfo.setLegalPersonPhone(request.getLegalRepresentativePhone());
            }

        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
        }
    }
}
