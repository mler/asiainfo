package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0020Request;
import com.bdx.rainbow.mapp.model.rsp.SK0020Response;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限列表接口
 */
@Service("sk0020")
@Action(bizcode="sk0020", version = "1.0", usercheck=false, ipcheck=false)
public class SK0020Action extends AbstractMappAction<SK0020Request, SK0020Response> {

    private static final String[] STATIC_MENUs = {"员工信息", "台账信息", "日常检查", "版本信息"};

    // 员工信息、台账信息、日常检查、版本信息
    @Override
    public void doAction(SK0020Request request, SK0020Response response) throws Exception {

        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if(null != sessionDetail) {
//            List<SysMenuTree> list = sysUserInfo.getuMenus();

            List<SK0020Response.Menu> menus = new ArrayList<>();
            for(int i = 0; i < 4; i++) {
                SK0020Response.Menu menu = new SK0020Response.Menu();

                menu.setId("0001");
                menu.setSysName(STATIC_MENUs[i]);

                menus.add(menu);
            }
//            for(SysMenuTree sysMenuTree : list) {
//                if(menus.size() == 4) {
//                    break;
//                }
//
//                SK0020Response.Menu menu = new SK0020Response.Menu();
//
//                menu.setId(sysMenuTree.getMenuId() + "");
//                menu.setSysName(sysMenuTree.getMenuName());
//
//                menus.add(menu);
//            }

            response.setMenus(menus);
        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
//            throw new BusinessException(new DefaultExceptionCode(ErrorCodeDefine.NO_USER_INFO, "登录超时!"));
        }
    }
}
