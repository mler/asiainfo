package com.bdx.rainbow.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.basic.common.SkuConstant;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.entity.basic.mysql.TBasicSku;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuImag;
import com.bdx.rainbow.service.IUserService;
import com.bdx.rainbow.service.basic.IBasicSkuService;
import com.bdx.rainbow.service.vfs.IFile;


/**
 * sku数据显示及维护
 * @author zhengwenjuan
 *
 */
@Controller
@RequestMapping("/sku")
public class SkuController {
	private final Logger logger = LoggerFactory.getLogger(SkuController.class);
	
//	@Autowired
//	private ISkuService skuService;
	
	@Autowired
	private IBasicSkuService basicSkuService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IFile fileService;
	
	@RequestMapping("/list")
    public ModelAndView pageList(TBasicSku sku,Model model, PageInfo pageInfo,String admin) throws Exception{
        	if (sku == null) {
				sku = new TBasicSku();
			}
        	if (StringUtils.equals(admin, "1")) {
        		model.addAttribute("admin", 1);
			}
        	if (StringUtils.isEmpty(sku.getStatus())) {
        		sku.setStatus("!" + SkuConstant.STATUS_DELETE);
			}

//            Map<String, Object> result = skuService.getSkuList(sku,pageInfo.getPageStart(), pageInfo.getPageCount());
            Map<String, Object> result = basicSkuService.getSkuList(sku,pageInfo.getPageStart(), pageInfo.getPageCount());
            
            List<TBasicSku> list = (List<TBasicSku>) result.get("list");
            int total = (Integer) result.get("total");

            pageInfo.setTotalCount(total);
            model.addAttribute("sku", sku);
            model.addAttribute("rows", list);
            model.addAttribute("pageInfo", pageInfo);
//        return new ModelAndView("jc/view");
            return new ModelAndView("basic/view");
    }
	
	@RequestMapping(value = "/showDetail")
	public String skuDetail(Model model,@RequestParam("skuBarcode") String skuBarcode,String admin) throws Exception{
		
//		JcSku sku = skuService.findSkuByCode(skuBarcode);
		TBasicSku sku = basicSkuService.getSkuByBarcode(skuBarcode);
		if (StringUtils.equals(admin, "1")) {
    		model.addAttribute("admin", 1);
		}
		
		/**
		if (StringUtils.isNotEmpty(sku.getImgs())) {
			String[] imgFields = sku.getImgs().split(",");
			List<String> imgList = new ArrayList<String>(imgFields.length);
			for (String imgField:imgFields) {
				String imgPath = skuService.findImgPathByFileId(imgField);
				if (StringUtils.isNotEmpty(imgPath)) {
					imgList.add(imgPath);
				}
				
			}
			model.addAttribute("imgs", imgList);
		}*/
		
		List<TBasicSkuImag> imgList = basicSkuService.getSkuImagsBySkuId(sku.getSkuId(), null);
		model.addAttribute("imgs", imgList);
		
		model.addAttribute("sku", sku);
		
//		return "jc/skuDetail";
		return "basic/skuDetail";
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public String modifySku(TBasicSku sku,Model model,@RequestParam("optType") Integer optType){
		logger.info("----------modifySku-------------");
		try {
			/**
			if (optType.intValue() == 2) {
				sku.setUpdateEmpCode("admin");
				sku.setAuditTime(now);
				sku.setStatus("2");
			}
			if (optType.intValue() == 1) {
				sku.setStatus("1");
			}
			sku.setUpdateEmpCode(Constants.UNKNOW_USER_ID.toString());
			sku.setUpdateTime(now);
			skuService.saveSku(sku);
			//fBuyerReqInfoService.updateRecommends(reqId, shopIds.size());
			 * 
			 */
			if (optType.intValue() == 2) {
				sku.setStatus(SkuConstant.STATUS_DELETE);//删除
			}
			if (optType.intValue() == 1) {
				sku.setStatus(SkuConstant.STATUS_NORMAL);//通过
			}
			basicSkuService.saveSkuNoDeleteImage(sku, null, null, null,Constants.UNKNOW_USER_ID.toString(), Constants.UNKNOW_USER_ID);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "{'flag':false,'msg':'" + e.getMessage() +"' }";
		}
		return "{'flag':true,'msg':'' }";
	}
	
	
	@RequestMapping(value = { "/skuImageUpload" }, method = RequestMethod.POST)
	public @ResponseBody
	void uploadImages(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		
		byte[] bytes = file.getBytes();

		String uploadDir = request.getRealPath("/") + "upload/sku";
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		String extName = FilenameUtils.getExtension(file.getOriginalFilename());
		System.out.println("extName:" + extName);
		String sep = System.getProperty("file.separator");
		File uploadedFile = new File(uploadDir + sep
				+ file.getOriginalFilename());
		FileCopyUtils.copy(bytes, uploadedFile);
//		String vfsId = skuService.justUploadImag(uploadedFile, extName, Constants.UNKNOW_USER_ID);
//		String url = skuService.findImgPathByFileId(vfsId);
		uploadedFile.delete();
//		response.getWriter().write(vfsId + ";" + url);
	}
	
}
