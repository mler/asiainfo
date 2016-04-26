package com.bdx.rainbow.controller.company;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.bean.DubboLicense;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.BaseController;
import com.bdx.rainbow.common.bean.ResultBean;
import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.service.ledger.ILedgerPurchaseService;
import com.bdx.rainbow.service.ledger.ILedgerService;
import com.bdx.rainbow.service.sk.*;
import com.bdx.rainbow.toolbox.SessionManager;
import com.bdx.rainbow.view.HealthView;
import com.bdx.rainbow.view.LedgerView;
import com.bdx.rainbow.ygcf.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 企业管理
 * Created by fusj on 16/3/2.
 */
@Controller
@RequestMapping(value = "company")
public class CompanyController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private static final String VM_ROOT_PATH = "/sk/company/%s";

    private static final String COMPANYLIST_VM_PATH = "/sk/companyList/%s";

    @Autowired
    private IEnterpriseDubboService enterpriseService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ILedgerService ledgerService;

    @Autowired
    private ILedgerAdditifService ledgerAdditifService;

    @Autowired
    private ILedgerPurchaseService ledgerPurchaseService;

    @Autowired
    private ILedgerDinnerService ledgerDinnerService;

    @Autowired
    private ILedgerSterilizeService ledgerSterilizeService;

    @Autowired
    private ILedgerTravelService ledgerTravelService;

    @Autowired
    private ILedgerWasteService ledgerWasteService;

    @Autowired
    private IDeviceService deviceService;

    /**
     * 获取企业详细信息
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"", "/"})
    public ModelAndView detail(Model model, HttpServletRequest request) throws Exception {

        Map<String, Object> map= enterpriseService.getEnterpriseDetailById(SessionManager.getEnterpriseId(request));

        DubboEnterpriseInfo enterpriseInfo = (DubboEnterpriseInfo) map.get("info");
        List<DubboLicense> list = (List<DubboLicense>) map.get("licenses");

        model.addAttribute("enterpriseInfo", enterpriseInfo);
        model.addAttribute("list", list);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detail"));
    }

    /**
     * 列表首页
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return String.format(COMPANYLIST_VM_PATH, "index");
    }

    /**
     * 列表数据页
     * @param model
     * @param enterpriseInfo
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ModelAndView list(Model model, PageInfo pageInfo, DubboEnterpriseInfo enterpriseInfo, HttpServletRequest request) throws Exception {

        Map<String, Object> result = enterpriseService.getEnterpriseInfos(enterpriseInfo, pageInfo.getPageStart(), pageInfo.getPageCount());

        pageInfo.setTotalCount(Integer.parseInt(result.get("total").toString()));
        pageInfo.setList((List) result.get("list"));

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "list"));
    }

    /**
     * 企业简介
     * @return
     */
    @RequestMapping(value = "companySimple", method = RequestMethod.GET)
    public String companySimple() {
        return String.format(COMPANYLIST_VM_PATH, "companySimple");
    }

    /**
     * 获取企业详细信息
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "companyDetail", method = RequestMethod.GET)
    public ModelAndView companyDetail(Model model, int enterpriseId) throws Exception {

        Map<String, Object> map= enterpriseService.getEnterpriseDetailById(enterpriseId);

        DubboEnterpriseInfo enterpriseInfo = (DubboEnterpriseInfo) map.get("info");
        List<DubboLicense> list = (List<DubboLicense>) map.get("licenses");

        model.addAttribute("enterpriseInfo", enterpriseInfo);
        model.addAttribute("list", list);

        return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "companyDetailInfo"));
    }

    /**
     * 企业详细查看健康证情况
     * @param model
     * @param enterpriseId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "companyHealthStat", method = RequestMethod.GET)
    public ModelAndView healthStat(Model model, int enterpriseId) throws Exception {

        List<Integer> idList = new ArrayList<>();
        idList.add(enterpriseId);

        Map<Integer, DubboEnterpriseInfo> map = dubboQueryEnterpriseInfoByIds(idList);
        if(!map.containsKey(enterpriseId)) {
            throw new Exception("企业编号:" + enterpriseId + ",不存在");
        }

        Map<String, Integer> statMap = employeeService.healthStat(enterpriseId);

        model.addAttribute("map", statMap);
        model.addAttribute("enterpriseInfo", map.get(enterpriseId));

        return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "healthStat"));
    }

    /**
     * 健康证统计数量显示
     * @param model
     * @param enterpriseId
     * @param type
     * @return
     */
    @RequestMapping(value = "statList", method = RequestMethod.GET)
    public ModelAndView statList(Model model, int enterpriseId, String type) {

        Employee employee = new Employee();
        employee.setEnterpriseId(enterpriseId);

        List<Employee> list = employeeService.statList(employee, type);

        List<HealthView> resultList = new ArrayList<>();
        for(Employee employeeData : list) {
            HealthView healthView = new HealthView();

            healthView.setFullName(employeeData.getFullName());
            healthView.setHealthCode(employeeData.getHealthCode());
            healthView.setValidDate(employeeData.getValidDate());
            healthView.setHealthPath(employeeData.getHealthPath());

            resultList.add(healthView);
        }

        model.addAttribute("list", resultList);

        return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "statList"));
    }

    /**
     * 查看台账信息
     * @param model
     * @return
     */
    @RequestMapping(value = "ledgerInfo", method = RequestMethod.GET)
    public ModelAndView ledgerInfo(Model model, int enterpriseId) {

        return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledgerInfo"));
    }

    /**
     * 台账首页
     * @return
     */
    @RequestMapping(value = "ledgerIndex", method = RequestMethod.GET)
    public String ledgerIndex(String type) {
        switch (type) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/purchase/index");
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                return String.format(COMPANYLIST_VM_PATH, "ledger/additif/index");
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/waste/index");
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/sterilize/index");
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                return String.format(COMPANYLIST_VM_PATH, "ledger/travel/index");
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                return String.format(COMPANYLIST_VM_PATH, "ledger/dinner/index");
            default:
                return String.format(COMPANYLIST_VM_PATH, "ledger/purchase/index");
        }
    }

    /**
     * 列表页数据加载
     *
     * @param
     * @return
     */
    @RequestMapping(value = "ledgerList", method = RequestMethod.POST)
    public ModelAndView listData(Model model, PageInfo pageInfo, Ledger ledger, String startDate, String endDate) throws Exception {

        pageInfo = ledgerService.list(ledger, startDate, endDate, pageInfo);

        List<LedgerView> resultList = new ArrayList<>();
        Set<Integer> enterpriseIds = new HashSet<>();

        // ledger对象转化为页面对象
        List<Ledger> list = pageInfo.getList();
        for (Ledger ledgerData : list) {
            LedgerView ledgerView = new LedgerView();
            ledgerView.setEnterpriseId(ledgerData.getEnterpriseId());
            ledgerView.setLedgerDate(ledgerData.getLedgerDate());
            ledgerView.setCreateDate(ledgerData.getCreateDate());
            ledgerView.setCreateUserid(ledgerData.getCreateUserid());
            ledgerView.setLedgerId(ledgerData.getLedgerId());

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

        switch (ledger.getLedgerType()) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/purchase/list"));
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/additif/list"));
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/waste/list"));
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/sterilize/list"));
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/travel/list"));
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/dinner/list"));
        }

        return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/additif/list"));
    }

    /**
     * 台账明细页面
     *
     * @return
     */
    @RequestMapping(value = "ledgerDetail", method = RequestMethod.GET)
    public String detail(@RequestParam("lid") int ledgerId) {
        Ledger ledger = ledgerService.get(ledgerId);

        switch (ledger.getLedgerType()) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/purchase/detail");
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                return String.format(COMPANYLIST_VM_PATH, "ledger/additif/detail");
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/waste/detail");
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/sterilize/detail");
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                return String.format(COMPANYLIST_VM_PATH, "ledger/travel/detail");
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                return String.format(COMPANYLIST_VM_PATH, "ledger/dinner/detail");
            default:
                return String.format(COMPANYLIST_VM_PATH, "ledger/purchase/detail");
        }
    }

    /**
     * 添加剂台账明细
     *
     * @param model
     * @param pageInfo
     * @param ledgerAdditif
     * @return
     */
    @RequestMapping(value = "ledgerAdditifDetailList", method = RequestMethod.POST)
    public ModelAndView additifDetailList(Model model, PageInfo pageInfo, LedgerAdditif ledgerAdditif) {

        pageInfo = ledgerAdditifService.list(ledgerAdditif, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "ledger/additif/detailList"));
    }

    /**
     * 采购台账明细
     * @param model
     * @param pageInfo
     * @param ledgerPurchase
     * @return
     */
    @RequestMapping(value = "ledgerPurchaseDetailList", method = RequestMethod.POST)
    public ModelAndView purchaseDetailList(Model model, PageInfo pageInfo, LedgerPurchase ledgerPurchase) {

        pageInfo = ledgerPurchaseService.list(ledgerPurchase, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detailList"));
    }

    /**
     * 集体用餐台账明细
     *
     * @param model
     * @param pageInfo
     * @param ledgerDinner
     * @return
     */
    @RequestMapping(value = "ledgerDinnerDetailList", method = RequestMethod.POST)
    public ModelAndView dinnerDetailList(Model model, PageInfo pageInfo, LedgerDinner ledgerDinner) {

        pageInfo = ledgerDinnerService.list(ledgerDinner, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detailList"));
    }

    /**
     * 消毒记录台账明细
     *
     * @param model
     * @param pageInfo
     * @param ledgerSterilize
     * @return
     */
    @RequestMapping(value = "ledgerSterilizeDetailList", method = RequestMethod.POST)
    public ModelAndView sterilizeDetailList(Model model, PageInfo pageInfo, LedgerSterilize ledgerSterilize) {

        pageInfo = ledgerSterilizeService.list(ledgerSterilize, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detailList"));
    }

    /**
     * 旅游接待台账明细
     *
     * @param model
     * @param pageInfo
     * @param ledgerTravel
     * @return
     */
    @RequestMapping(value = "ledgerTravelDetailList", method = RequestMethod.POST)
    public ModelAndView travelDetailList(Model model, PageInfo pageInfo, LedgerTravel ledgerTravel) {

        pageInfo = ledgerTravelService.list(ledgerTravel, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detailList"));
    }

    /**
     * 台账明细列表数据
     *
     * @param model
     * @param pageInfo
     * @param ledgerWaste
     * @return
     */
    @RequestMapping(value = "ledgerWasteDetailList", method = RequestMethod.POST)
    public ModelAndView wasteDetailList(Model model, PageInfo pageInfo, LedgerWaste ledgerWaste) {

        pageInfo = ledgerWasteService.list(ledgerWaste, pageInfo);

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detailList"));
    }

    /**
     * 台账票证页面
     *
     * @return
     */
    @RequestMapping(value = "ledgerTicket", method = RequestMethod.GET)
    public String ledgerTicket(@RequestParam("lid") int ledgerId) {
        Ledger ledger = ledgerService.get(ledgerId);

        switch (ledger.getLedgerType()) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/purchase/ticket");
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                return String.format(COMPANYLIST_VM_PATH, "ledger/additif/ticket");
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/waste/ticket");
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                return String.format(COMPANYLIST_VM_PATH, "ledger/sterilize/ticket");
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                return String.format(COMPANYLIST_VM_PATH, "ledger/travel/ticket");
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                return String.format(COMPANYLIST_VM_PATH, "ledger/dinner/ticket");
            default:
                return String.format(COMPANYLIST_VM_PATH, "ledger/purchase/ticket");
        }
    }

    /**
     * 根据企业ID获取企业对象
     * @param id
     * @return
     */
    @RequestMapping(value = "getByEnterpriseId", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean getByEnterpriseId(int id) {
        try {

            List<Integer> idList = new ArrayList<>();
            idList.add(id);

            Map<Integer, DubboEnterpriseInfo> map = dubboQueryEnterpriseInfoByIds(idList);
            if(!map.containsKey(id)) {
                throw new Exception("企业编号:" + id + ",不存在");
            }

            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(map.get(id));

            return resultBean;

        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 视频监控页面
     * @param model
     * @param enterpriseId
     * @return
     */
    @RequestMapping(value = "monitor", method = RequestMethod.GET)
    public ModelAndView monitor(Model model, @RequestParam("id") int enterpriseId) {
        try {
            List<Integer> idList = new ArrayList<>();
            idList.add(enterpriseId);

            Map<Integer, DubboEnterpriseInfo> map = dubboQueryEnterpriseInfoByIds(idList);
            if(!map.containsKey(enterpriseId)) {
                throw new Exception("企业编号:" + enterpriseId + ",不存在");
            }

            // 设备列表
            List<Device> deviceList = deviceService.selectByList(enterpriseId);

            model.addAttribute("enterpriseInfo", map.get(enterpriseId));
            model.addAttribute("deviceList", deviceList);

            return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "monitor"));

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ModelAndView(String.format(COMPANYLIST_VM_PATH, "monitor"));
        }
    }

    /**
     * 显示视频监控
     * @return
     */
    @RequestMapping(value = "showMonitor", method = RequestMethod.GET)
    public String showMonitor() {
        return String.format(COMPANYLIST_VM_PATH, "showMonitor");
    }

    /**
     * 获取监控列表
     * @param id
     * @return
     */
    @RequestMapping(value = "getDeviceSn", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean getDeviceSn(@RequestParam("id") int enterpriseId) {
        try {
            ResultBean resultBean = new ResultBean(true);

            List<Device> deviceList = deviceService.selectByList(enterpriseId);

            // 为空则不返回数据
            if(!CollectionUtils.isEmpty(deviceList)) {
                resultBean.setData(deviceList.get(0));
            }

            return resultBean;

        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }
}
