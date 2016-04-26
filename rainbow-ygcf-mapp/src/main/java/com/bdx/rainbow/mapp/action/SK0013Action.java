package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0013Request;
import com.bdx.rainbow.mapp.model.rsp.SK0013Response;
import com.bdx.rainbow.service.mapp.IBasicService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by core on 15/10/19.
 * 企业信息
 */
@Service("sk0013")
@Action(bizcode="sk0013",version = "1.0",usercheck=false, ipcheck=false)
public class SK0013Action extends AbstractMappAction<SK0013Request,SK0013Response> {

    @Autowired
    private IBasicService basicService;

    @Override
    public void doAction(SK0013Request request, SK0013Response response) throws Exception {

        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if(null != sessionDetail && null != sessionDetail.getCorpId()) {
            DubboEnterpriseInfo enterpriseInfo = basicService.getEnterpriseById(sessionDetail.getCorpId());

            response.setBusinessLicenseNumber(enterpriseInfo.getBusinessLicence());
            response.setCompanyName(enterpriseInfo.getEnterpriseName());
            response.setContactPhone(enterpriseInfo.getLegalPersonPhone());
            response.setEconomicNature(enterpriseInfo.getEnterpriseType());
            response.setFixedAssets(enterpriseInfo.getRegiCapital());
            response.setResponsiblePersons(enterpriseInfo.getLegalPerson());
            response.setLegalRepresentative(enterpriseInfo.getLegalPerson());
            response.setLegalRepresentativePhone(enterpriseInfo.getLegalPersonPhone());
            response.setLicenseNumber(enterpriseInfo.getBusinessLicence());
            response.setFileUrl("");
            response.setValidity("");
            response.setHonesty("");
            response.setMaterialSource("");
            response.setPermissionType("");
            response.setMaterialSource("");
        }  else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
        }
    }
}
