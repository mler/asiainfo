package com.bdx.rainbow.controller.employee;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.BaseController;
import com.bdx.rainbow.common.bean.ResultBean;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.service.sk.IEmployeeService;
import com.bdx.rainbow.service.sk.IHealthService;
import com.bdx.rainbow.view.DeviceView;
import com.bdx.rainbow.view.EmployeeView;
import com.bdx.rainbow.view.HealthView;
import com.bdx.rainbow.ygcf.entity.Device;
import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.Health;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 企业员工信息
 * Created by fusj on 16/3/2.
 */
@Controller
@RequestMapping("employee")
public class EmployeeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private static final String VM_ROOT_PATH = "/sk/employee/%s";

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IHealthService healthService;

    /**
     * 企业员工首页
     * @return
     */
    @RequestMapping(value = {"", "/", "index"}, method = RequestMethod.GET)
    public String index() {
        return String.format(VM_ROOT_PATH, "index");
    }

    /**
     * 企业员工列表
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ModelAndView list(Model model, PageInfo pageInfo, Employee employee, String enterpriseName) throws Exception {

        List<DubboEnterpriseInfo> list = dubboQueryEnterpriseByName(enterpriseName);

        if(StringUtil.isNotEmptyObject(enterpriseName) && CollectionUtils.isEmpty(list)) {
            pageInfo.setList(new ArrayList());
            pageInfo.setTotalCount(0);
        } else {
            pageInfo = employeeService.list(employee, pageInfo, getEnterpriseIdList(list));


            Set<Integer> enterpriseIds = new HashSet<>();
            List<EmployeeView> resultList = new ArrayList<>();

            // 模型转化
            for (Employee employeeData : (List<Employee>) pageInfo.getList()) {
                enterpriseIds.add(employeeData.getEnterpriseId());

                EmployeeView employeeView = new EmployeeView();

                employeeView.setEmployeeId(employeeData.getEmployeeId());
                employeeView.setFullName(employeeData.getFullName());
                employeeView.setSex(employeeData.getSex());
                employeeView.setIsneedHealth(employeeData.getIsneedHealth());
                employeeView.setPost(employeeData.getPost());
                employeeView.setJobTitle(employeeData.getJobTitle());
                employeeView.setEnterpriseId(employeeData.getEnterpriseId());
                employeeView.setPersonalPhone(employeeData.getPersonalPhone());

                resultList.add(employeeView);
            }

            // 查询企业信息
            Map<Integer, DubboEnterpriseInfo> map = dubboQueryEnterpriseInfoByIds(new ArrayList<>(enterpriseIds));

            for (EmployeeView employeeView : resultList) {
                DubboEnterpriseInfo enterpriseInfo = map.get(employeeView.getEnterpriseId());
                employeeView.setEnterpriseName(enterpriseInfo.getEnterpriseName());
            }

            pageInfo.setList(resultList);
        }

        model.addAttribute("pageInfo", pageInfo);

        return new ModelAndView(String.format(VM_ROOT_PATH, "list"));
    }

    /**
     * 企业员工详细页面
     * @param model
     * @param employee
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ModelAndView detail(Model model, Employee employee) throws Exception {

        employee = employeeService.get(employee.getEmployeeId());
        Health health = healthService.selectByEmployeeId(employee.getEmployeeId());

        model.addAttribute("employee", employee);
        model.addAttribute("health", health);

        return new ModelAndView(String.format(VM_ROOT_PATH, "detail"));
    }

    /**
     * 企业员工编辑页面
     * @param model
     * @param employee
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView edit(Model model, Employee employee) throws Exception {

        employee = employeeService.get(employee.getEmployeeId());
        Health health = healthService.selectByEmployeeId(employee.getEmployeeId());

        model.addAttribute("employee", employee);
        model.addAttribute("health", health);

        return new ModelAndView(String.format(VM_ROOT_PATH, "edit"));
    }

    /**
     * 企业员工编辑保存
     * @param employee
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean edit(Employee employee) {
        try {
            employeeService.edit(employee);

            ResultBean resultBean = new ResultBean(true);

            return resultBean;

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ajaxException(ex);
        }
    }

    /**
     * 企业员工新增
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String employeeAdd() {
        return String.format(VM_ROOT_PATH, "add");
    }

    /**
     * 新增保存
     * @param employee
     * @param health
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean add(Employee employee, Health health) {
        try {

            int employeeId = employeeService.addEmployee(employee, health);

            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(employeeId);

            return resultBean;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ajaxException(ex);
        }
    }

    /**
     * 换证页面
     * @return
     */
    @RequestMapping(value = "change", method = RequestMethod.GET)
    public ModelAndView changeHealth(Model model, Employee employee) {
        employee = employeeService.get(employee.getEmployeeId());

        model.addAttribute("employee", employee);

        return new ModelAndView(String.format(VM_ROOT_PATH, "changeHealth"));
    }

    /**
     * 健康证列表
     * @param model
     * @param employee
     * @return
     */
    @RequestMapping(value = "healthList", method = RequestMethod.GET)
    public ModelAndView healthList(Model model, Employee employee) {
        employee = employeeService.get(employee.getEmployeeId());
        List<Health> list = healthService.selectAllByEmployeeId(employee.getEmployeeId());

        model.addAttribute("employee", employee);
        model.addAttribute("list", list);

        return new ModelAndView(String.format(VM_ROOT_PATH, "healthList"));
    }

    /**
     * 换证保存
     * @param health
     * @return
     */
    @RequestMapping(value = "change", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean change(Health health) {
        try {
            healthService.change(health);

            ResultBean resultBean = new ResultBean(true);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 删除
     * @param employee
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean del(Employee employee) {
        try {

            employeeService.del(employee);

            ResultBean resultBean = new ResultBean(true);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 健康证统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "stat", method = RequestMethod.GET)
    public ModelAndView healthStat(Model model, Employee employee) {

        Map<String, Integer> map = employeeService.healthStat(1);

        model.addAttribute("map", map);

        return new ModelAndView(String.format(VM_ROOT_PATH, "healthStat"));
    }

    /**
     * 健康证统计数量显示
     * @param model
     * @param employee
     * @param type
     * @return
     */
    @RequestMapping(value = "statList", method = RequestMethod.GET)
    public ModelAndView statList(Model model, Employee employee, String type) {

        employee.setEnterpriseId(1);

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

        return new ModelAndView(String.format(VM_ROOT_PATH, "statList"));
    }

    /**
     * 健康证统计页面
     * @return
     */
    @RequestMapping(value = "healthStat", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean healthStat(int id) {
        try {
            Map<String, Integer> map = employeeService.healthStat(id);

            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(map);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }
}
