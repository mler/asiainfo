package com.bdx.rainbow.spsy.controller.syjg;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseLicense;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.service.IEnterpriseLicenseService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/3/9.
 */
@Controller
@RequestMapping("supLicense")
public class LicenseController {
    private final static Logger log= LoggerFactory.getLogger(LicenseController.class);
    @Autowired
    private IEnterpriseLicenseService licenseService;
    @RequestMapping(value = {"/init"}, method = RequestMethod.GET)
    public ModelAndView init(Model model)throws Exception{
        return new ModelAndView("/supLicense/licenseInit");
    }
    @RequestMapping(value = "/list")
    public ModelAndView list(Model model,PageInfo pageinfo,HttpServletRequest request) throws BusinessException {
        DubboEnterpriseLicense condition=new DubboEnterpriseLicense();
        condition.setEnterpriseName(request.getParameter("enterpriseName"));
        condition.setLicenseCode(request.getParameter("licenseCode"));
        condition.setOrganizationCode(request.getParameter("organizationCode"));
        if (StringUtils.isNotBlank(request.getParameter("validDateStart"))){
            condition.setValidDateStart(DateUtil.getTimestamp(request.getParameter("validDateStart")));
        }
        if (StringUtils.isNotBlank(request.getParameter("validDateEnd"))){
           condition.setValidDateEnd(DateUtil.getTimestamp(request.getParameter("validDateEnd")));
        }
        String type=request.getParameter("type")==null?"":request.getParameter("type");
        if("0".equals(type)){
            condition.setInvalidDateStart(DateUtil.getCurrent());
        }else if("1".equals(type)){
            condition.setInvalidDateStart(DateUtil.getCurrent());
            condition.setInvalidDateEnd(new Timestamp(DateUtil.addMonth(new Date(),1).getTime()));
        }else if("-1".equals(type)){
            condition.setInvalidDateEnd(DateUtil.getCurrent());
        }
        Map<String, Object> resultMap =new HashMap<String, Object>();
        try {
            resultMap =licenseService.getLicenses(condition,pageinfo.getPageStart(),pageinfo.getPageCount());
            pageinfo.setTotalCount((Integer) resultMap.get("total"));
            model.addAttribute("rows",resultMap.get("list"));
            model.addAttribute("pageinfo",pageinfo);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return new ModelAndView("/supLicense/licenseList");
    }
    @RequestMapping("/excelOut")
    public void companyInfoExcelOut(HttpServletRequest request,HttpServletResponse response) {
        DubboEnterpriseLicense condition=new DubboEnterpriseLicense();
        condition.setEnterpriseName(request.getParameter("enterpriseName"));
        condition.setLicenseCode(request.getParameter("licenseCode"));
        condition.setOrganizationCode(request.getParameter("organizationCode"));
        if (StringUtils.isNotBlank(request.getParameter("validDateStart"))){
            condition.setValidDateStart(DateUtil.getTimestamp(request.getParameter("validDateStart")));
        }
        if (StringUtils.isNotBlank(request.getParameter("validDateEnd"))){
            condition.setValidDateEnd(DateUtil.getTimestamp(request.getParameter("validDateEnd")));
        }
        String type=request.getParameter("type")==null?"":request.getParameter("type");
        if("0".equals(type)){
            condition.setInvalidDateStart(DateUtil.getCurrent());
        }else if("1".equals(type)){
            condition.setInvalidDateStart(DateUtil.getCurrent());
            condition.setInvalidDateEnd(new Timestamp(DateUtil.addMonth(new Date(),1).getTime()));
        }else if("-1".equals(type)){
            condition.setInvalidDateEnd(DateUtil.getCurrent());
        }
        Map<String, Object> resultMap =new HashMap<String, Object>();
        try {
            resultMap =licenseService.getLicenses(condition,-1,0);
            String title ="企业许可证";
            String[] headers={"企业名称","组织机构代码","法人","联系方式","许可证类型","许可证编号","申请日期","当前状态"};
            HSSFWorkbook wb = licenseService.ExcelOut(title,headers,(List<DubboEnterpriseLicense>)resultMap.get("list"),null);
            response.setContentType("application/vnd.ms-excel");
            String fileName="licenses.xls";
            response.setHeader("Content-disposition", "attachment;filename="+fileName);
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }
}
