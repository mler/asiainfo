package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0015Request;
import com.bdx.rainbow.mapp.model.rsp.SK0015Response;
import com.bdx.rainbow.service.sk.IDeviceService;
import com.bdx.rainbow.ygcf.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 监控设备列表
 * Created by core on 15/10/20.
 */
@Service("sk0015")
@Action(bizcode="sk0015", version = "1.0", usercheck=false, ipcheck=false)
public class SK0015Action extends AbstractMappAction<SK0015Request, SK0015Response> {

    @Autowired
    private IDeviceService deviceService;

    @Override
    public void doAction(SK0015Request request, SK0015Response response) throws Exception {
        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        int enterpriseId = 0;

        if(null != sessionDetail) {
            // 前段传了企业编号，根据企业编号查询
            if(StringUtil.isNotEmptyObject(request.getEnterpriseId())) {
                enterpriseId = Integer.parseInt(request.getEnterpriseId());
            }
            // 没有传企业编号，按当前登陆用户
            else {
                if(null != sessionDetail.getCorpId()) {
                    enterpriseId = sessionDetail.getCorpId();
                } else {
                    throw new ActionException(ErrorCodeDefine.EXPECT_ERROR, "用户企业编号为空");
                }
            }

            List<Device> list = deviceService.selectByList(enterpriseId);

            List<SK0015Response.Device> devices = new ArrayList<SK0015Response.Device>();
            for(Device device : list) {
                SK0015Response.Device view = new SK0015Response.Device();

                view.setDeviceName(device.getDeviceName());
                view.setDeviceSn(device.getDeviceKey());

                devices.add(view);
            }

            response.setDevices(devices);

        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
        }
    }
}
