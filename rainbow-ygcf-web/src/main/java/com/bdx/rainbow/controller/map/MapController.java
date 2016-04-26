package com.bdx.rainbow.controller.map;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.entity.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 企业地图
 * Created by fusj on 16/3/2.
 */
@Controller
@RequestMapping("map")
public class MapController {
    private static final Logger logger = LoggerFactory.getLogger(MapController.class);

    private static final String VM_ROOT_PATH = "sk/map/%s";

    @Autowired
    private IEnterpriseDubboService enterpriseService;

    /**
     * 地图首页
     * @return
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return String.format(VM_ROOT_PATH, "map_index");
    }

    /**
     * 列表页
     * @param model
     * @param pageInfo
     * @param enterpriseInfo
     * @return
     */
    @RequestMapping(value = "list")
    public ModelAndView list(Model model, PageInfo pageInfo, DubboEnterpriseInfo enterpriseInfo) throws Exception{

        Map<String, Object> result = enterpriseService.getEnterpriseInfos(enterpriseInfo, pageInfo.getPageStart(), pageInfo.getPageCount());

        pageInfo.setTotalCount(Integer.parseInt(result.get("total").toString()));
        pageInfo.setList((List) result.get("list"));

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "list"));
    }
}
