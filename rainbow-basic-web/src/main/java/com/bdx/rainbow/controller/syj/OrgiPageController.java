package com.bdx.rainbow.controller.syj;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.entity.etl.OrgiPage;
import com.bdx.rainbow.service.etl.IOrgiPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * ORGI_PAGE数据展示
 * Created by fusj on 2015/8/25.
 */
@Controller
@RequestMapping("/orgipage")
public class OrgiPageController {

    private final Logger logger = LoggerFactory.getLogger(OrgiPageController.class);

    @Autowired
    private IOrgiPageService orgiPageService;

    @RequestMapping("/list")
    public ModelAndView pageList(Model model, PageInfo pageInfo) {
        try {
            List<OrgiPage> list = orgiPageService.getOrgiPageList(pageInfo.getPageStart(), pageInfo.getPageCount());
            int total = orgiPageService.countOrgiPage();

            pageInfo.setTotalCount(total);

            model.addAttribute("rows", list);
            model.addAttribute("pageInfo", pageInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ModelAndView("syj/view");
    }
}
