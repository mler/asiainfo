package com.bdx.rainbow.controller.syj;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.entity.etl.SyjTableBean;
import com.bdx.rainbow.service.etl.ISyjTableBeanService;
import com.bdx.rainbow.service.jc.IProductAnalyzeService;

/**
 * 食药监数据抓取关联表
 * Created by Administrator on 2015/8/26.
 */
@Controller
@RequestMapping("/syjTable")
public class SyjTableBeanController {

    private final Logger logger = LoggerFactory.getLogger(SyjTableBeanController.class);

    @Autowired
    private ISyjTableBeanService syjTableBeanService;
    
    @Autowired
    private IProductAnalyzeService productAnalyzeService;

    /**
     *  列表数据
     * @param model
     * @param pageInfo
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public ModelAndView list(Model model, PageInfo pageInfo) throws Exception {
        try {
        	
//        	productAnalyzeService.csdnAnalyzeByDoc(null);
        	
            List<SyjTableBean> list = syjTableBeanService.pageList(pageInfo.getPageStart(), pageInfo.getPageCount());

            int count = syjTableBeanService.countPageList();
            pageInfo.setTotalCount(count);

            model.addAttribute("rows", list);
            model.addAttribute("pageInfo", pageInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new ModelAndView("syj/tableList");
    }
    
    
}
