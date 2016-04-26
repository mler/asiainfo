package com.bdx.rainbow.controller.ledger;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.BaseController;
import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.ledger.ILedgerPurchaseService;
import com.bdx.rainbow.service.ledger.ILedgerService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.toolbox.SessionManager;
import com.bdx.rainbow.view.LedgerView;
import com.bdx.rainbow.ygcf.entity.Ledger;
import com.bdx.rainbow.ygcf.entity.LedgerPurchase;
import com.bdx.rainbow.ygcf.entity.LedgerPurchaseExample;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 采购台账
 * Created by fusj on 16/1/22.
 */
@Controller
@RequestMapping("ledgerPurchase")
public class LedgerPurchaseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LedgerPurchaseController.class);

    // vm文件存放根目录
    private static final String VM_ROOT_PATH = "sk/ledger/purchase/%s";

    @Autowired
    private ILedgerService ledgerService;

    @Autowired
    private ILedgerPurchaseService ledgerPurchaseService;

    /**
     * 列表页
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return String.format(VM_ROOT_PATH, "index");
    }

    /**
     * 列表页数据加载
     * @param
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ModelAndView listData(Model model, PageInfo pageInfo, Ledger ledger, String startDate, String endDate, HttpServletRequest request) throws Exception {
        ledger.setLedgerType(LedgerTypeConstants.LEDGER_TYPE_PURCHASE);

        // 企业编号
        ledger.setEnterpriseId(SessionManager.getEnterpriseId(request));

        pageInfo = ledgerService.list(ledger, startDate, endDate, pageInfo);

        List<LedgerView> resultList = new ArrayList<>();
        Set<Integer> enterpriseIds = new HashSet<>();

        // ledger对象转化为页面对象
        List<Ledger> list = pageInfo.getList();
        for(Ledger ledgerData : list) {
            LedgerView ledgerView = new LedgerView();

            ledgerView.setLedgerId(ledgerData.getLedgerId());
            ledgerView.setEnterpriseId(ledgerData.getEnterpriseId());
            ledgerView.setLedgerDate(ledgerData.getLedgerDate());
            ledgerView.setCreateDate(ledgerData.getCreateDate());
            ledgerView.setCreateUserid(ledgerData.getCreateUserid());

            resultList.add(ledgerView);
            enterpriseIds.add(ledgerData.getEnterpriseId());
        }

        Map<Integer, DubboEnterpriseInfo> map = dubboQueryEnterpriseInfoByIds(new ArrayList<Integer>(enterpriseIds));

        for(LedgerView ledgerView : resultList) {
            DubboEnterpriseInfo enterpriseInfo = map.get(ledgerView.getEnterpriseId());

            if(null != enterpriseInfo) {
                ledgerView.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                ledgerView.setLegalPerson(enterpriseInfo.getLegalPerson());
            }
        }

        pageInfo.setList(resultList);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "list"));
    }

    /**
     * 台账明细页面
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail() {
        return String.format(VM_ROOT_PATH, "detail");
    }

    /**
     * 台账明细列表数据
     * @param model
     * @param pageInfo
     * @param ledgerPurchase
     * @return
     */
    @RequestMapping(value = "detailList", method = RequestMethod.POST)
    public ModelAndView detailList(Model model, PageInfo pageInfo, LedgerPurchase ledgerPurchase) {

        pageInfo = ledgerPurchaseService.list(ledgerPurchase, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detailList"));
    }

    /**
     * 票证页面
     * @return
     */
    @RequestMapping(value = "ticket", method = RequestMethod.GET)
    public String ticket() {
        return String.format(VM_ROOT_PATH, "ticket");
    }

    /**
     * 票证列表获取
     * @param model
     * @return
     */
    @RequestMapping(value = "ticketList", method = RequestMethod.GET)
    public ModelAndView ticketList(Model model, LedgerPurchase ledgerPurchase) {
        try {
            Map<String, String> map = new HashMap<>();
            // 采购明细ID不为空
            if(StringUtil.isNotEmptyObject(ledgerPurchase.getPurchaseId())) {
                map = ledgerPurchaseService.ticketSingle(ledgerPurchase.getPurchaseId());

                ledgerPurchase = ledgerPurchaseService.get(ledgerPurchase.getPurchaseId());
            }
            // 台账ID不为空
            else if(StringUtil.isNotEmptyObject(ledgerPurchase.getLedgerId())) {
                map = ledgerPurchaseService.ticketList(ledgerPurchase.getLedgerId());
            }

            model.addAttribute("map", map);
            model.addAttribute("ledgerId", ledgerPurchase.getLedgerId());

            return new ModelAndView(String.format(VM_ROOT_PATH, "ticketList"));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ModelAndView(String.format(VM_ROOT_PATH, "ticketList"));
        }
    }

    /**
     * 票证关联台账明细列表
     * @param model
     * @param pageInfo
     * @param ledgerPurchase
     * @return
     */
    @RequestMapping(value = "ticketDetailList", method = RequestMethod.POST)
    public ModelAndView detailDetailList(Model model, PageInfo pageInfo, LedgerPurchase ledgerPurchase) {

        pageInfo = ledgerPurchaseService.list(ledgerPurchase, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "ticketDetailList"));
    }
}
