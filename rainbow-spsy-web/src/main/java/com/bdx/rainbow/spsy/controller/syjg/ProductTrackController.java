package com.bdx.rainbow.spsy.controller.syjg;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginInspectionInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockDetailIn;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;
import com.bdx.rainbow.spsy.service.IProductTrackService;
import com.bdx.rainbow.spsy.service.ITInspectionInfoService;
import com.bdx.rainbow.spsy.service.ITraceService;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;
import org.apache.commons.lang.StringUtils;
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
 * Created by core on 16/3/2.
 * 食品追踪
 */
@Controller
@RequestMapping("track")
public class ProductTrackController {
    private final static Logger log= LoggerFactory.getLogger(ProductTrackController.class);
    @Autowired
    private IProductTrackService trackService;
    @Autowired
    private IFileDubboService fileDubboService;

    @Autowired
    private ITInspectionInfoService inspectionInfoService;
    @Autowired
    private IAgencyCompanyService agencyCompanyService;
    @Autowired
    private ITraceService traceService;

    @RequestMapping(value = {"/init"}, method = RequestMethod.GET)
    public ModelAndView init(Model model)throws Exception{
        log.info("商品追踪Init。。。");
        return new ModelAndView("/track/trackInit");
    }

    @RequestMapping("/list")
    public ModelAndView list(Model model,@RequestParam Map stockOut){
        log.info("商品追踪List。。。");
        try {
            if (StringUtils.isEmpty(stockOut.get("skuBatch").toString())&&StringUtils.isEmpty(stockOut.get("skuBarCode").toString())){
                TOriginProducerStockDetailIn detailIn=traceService.getSkuBarCodeAndBatchBySYM(stockOut.get("productIdCode").toString());
                if (detailIn!=null&&StringUtils.isNotEmpty(detailIn.getSkuBarCode())&&StringUtils.isNotEmpty(detailIn.getSkuBatch())){
                    stockOut.put("skuBatch",detailIn.getSkuBatch());
                    stockOut.put("skuBarCode",detailIn.getSkuBarCode());

                }else {
                    return new ModelAndView("/track/list");
                }
            }
            List<Map> lists= trackService.getProducer(stockOut);
//            Map<Integer, DubboEnterpriseInfo> result =new HashMap<Integer, DubboEnterpriseInfo>();
//            result=trackService.getEnterprise(lists);
            model.addAttribute("rows",lists);
//            model.addAttribute("maps",result);
        }catch (Exception e){
            log.debug(e.getMessage(), e);
        }
        return new ModelAndView("/track/trackList");
    }

    @RequestMapping("/agencyList")
        public ModelAndView agencyList(Model model,@RequestParam Map stockOut,PageInfo pageInfo)throws Exception{
        log.info("经销商List。。。");
        HashMap map=new HashMap();
        if (StringUtils.isEmpty(stockOut.get("skuBatch").toString())&&StringUtils.isEmpty(stockOut.get("skuBarCode").toString())){
            TOriginProducerStockDetailIn detailIn=traceService.getSkuBarCodeAndBatchBySYM(stockOut.get("productIdCode").toString());
            if (detailIn!=null&&StringUtils.isNotEmpty(detailIn.getSkuBarCode())&&StringUtils.isNotEmpty(detailIn.getSkuBatch())){
                stockOut.put("skuBatch",detailIn.getSkuBatch());
                stockOut.put("skuBarCode",detailIn.getSkuBarCode());

            }else {
                return new ModelAndView("/track/agencyList");
            }
        }
        List<Integer> buyAgencyIds=trackService.getFirAgencyIds(stockOut);
        try {
            if (buyAgencyIds!=null&&buyAgencyIds.size()>0){
                stockOut.put("buyAgencyIds", buyAgencyIds);
                map=(HashMap)trackService.getCirculates(stockOut);
                int agencys=map.entrySet().size();
                stockOut.put("buyAgencyIds",new ArrayList(map.keySet()));
                while(agencys>0){
                    HashMap newMap=(HashMap)trackService.getCirculates(stockOut);
                    agencys=0;
                    if (newMap.size()>0){
                        Iterator<Integer> iterator = newMap.keySet().iterator();
                        while (iterator.hasNext()){
                            Integer key=iterator.next();
                            if (key!=0){
                                map.put(key,newMap.get(key));
                                agencys++;
                            }
                        }
                        stockOut.put("buyAgencyIds",new ArrayList(newMap.keySet()));
                    }
                }
                model.addAttribute("rows",map);
            }
        }catch (Exception e){
            log.debug(e.getMessage(), e);
        }
        return new ModelAndView("/track/agencyList");
    }
    @RequestMapping("/saleList")
    public ModelAndView saleList(Model model,@RequestParam Map stock,PageInfo pageInfo)throws Exception{
        log.info("零售商List。。。");
        if (StringUtils.isEmpty(stock.get("skuBatch").toString())&&StringUtils.isEmpty(stock.get("skuBarCode").toString())){
            TOriginProducerStockDetailIn detailIn=traceService.getSkuBarCodeAndBatchBySYM(stock.get("productIdCode").toString());
            if (detailIn!=null&&StringUtils.isNotEmpty(detailIn.getSkuBarCode())&&StringUtils.isNotEmpty(detailIn.getSkuBatch())){
                stock.put("skuBatch",detailIn.getSkuBatch());
                stock.put("skuBarCode",detailIn.getSkuBarCode());

            }else {
                return new ModelAndView("/track/saleList");
            }
        }
        HashMap outs=(HashMap)trackService.getSales(stock);
        model.addAttribute("rows",outs);
        return new ModelAndView("/track/saleList");
    }
    @RequestMapping("/showInspection")
    public ModelAndView showInspection(Model model, HttpServletRequest request)throws Exception{
        log.info("检测报告查看。。。");
        String detectionId = request.getParameter("productDetectionId");
        TOriginInspectionInfo inspectionInfo = inspectionInfoService.getInspectionInfo(Integer.parseInt(detectionId));
        String fileId=inspectionInfo.getDetectionReport();
        if (fileId!=null&&StringUtils.isNotBlank(fileId)){
            String path=fileDubboService.getHttpUrl(fileId,"VFS_RAINBOW_FILE_SERVER_DUBBO");
            model.addAttribute("path",path);
        }
        model.addAttribute("inspectionInfo",inspectionInfo);
        model.addAttribute("skuName",request.getParameter("skuName"));
        return new ModelAndView("/track/inspectionShow");
    }
    @RequestMapping("/showAgency")
    public ModelAndView showAgency(Model model, HttpServletRequest request)throws Exception{
        log.info("经销商查看。。。");
        String agencyId = request.getParameter("agencyId");
        TOriginAgencyCompany agency = agencyCompanyService.getAgency(Integer.parseInt(agencyId));
        model.addAttribute("agency", agency);
        return new ModelAndView("/track/agencyShow");
    }
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public String tree(@RequestParam Map stock) throws Exception{
       return "";
    }
}
