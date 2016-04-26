package com.bdx.rainbow.spsy.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.common.MatrixToImageWriterEx;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginIdentificationCodeDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginIdentificationCodeDetailDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCode;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeDetail;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeDetailExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeExample.Criteria;
import com.bdx.rainbow.spsy.service.IIdentificationCodeService;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;
import com.google.zxing.common.BitMatrix;

public class IdentificationCodeServiceImpl implements IIdentificationCodeService {
    @Autowired
    private TOriginIdentificationCodeDAO originIdentificationCodeDAO;
    
    @Autowired
    private TOriginIdentificationCodeDetailDAO originIdentificationCodeDetailDAO;
	
    @Autowired
	private IFileDubboService fileDubboService;
    
	@Override
	public List<TOriginIdentificationCode> getCodeList(TOriginIdentificationCode condition, int start, int limit) throws BusinessException {
		TOriginIdentificationCodeExample example = convertExample(condition);
		if(start >= 0){
			example.setLimitClauseCount(limit);
			example.setLimitClauseStart(start);
		}
		return originIdentificationCodeDAO.selectByExample(example);
	}

	@Override
	public Integer countCodes(TOriginIdentificationCode condition) throws BusinessException {
		TOriginIdentificationCodeExample example = convertExample(condition);
		return originIdentificationCodeDAO.countByExample(example);
	}
    
	@Override
	public void insertCode(TOriginIdentificationCode code, String skuIdCodeType,int userId) throws Exception {
		code.setCreateTime(DateUtil.getCurrent());	
		if(code.getCodeQuantity()==null){
			code.setCodeQuantity(1);
		}
		int buildId = originIdentificationCodeDAO.insertSelective(code);
		
		int count = code.getCodeQuantity();
		TOriginIdentificationCodeDetail codeDetail = new TOriginIdentificationCodeDetail();
		codeDetail.setSkuIdCodeType(skuIdCodeType);
        codeDetail.setCreateStaff(code.getCreateStaff());
        codeDetail.setCreateTime(code.getCreateTime());
        codeDetail.setBuildId(buildId);
        codeDetail.setCodeStatus(SpsyConstants.CODE_UN_USE);
		for(int i = 0; i < count; i++){
			String codeNum = generationID(code.getCodeType(),code.getSkuBarcode(),code.getSkuBatch());
			codeDetail.setCodeNumber(codeNum);
			String fileId = this.getQrCodeUrl(codeNum,userId,"code"+i);
			codeDetail.setCodeVfsId(fileId);
			originIdentificationCodeDetailDAO.insertSelective(codeDetail);			
		}
	}
	
	private String getQrCodeUrl(String codeNum,int userId,String fileName) throws Exception{
		BitMatrix bitMatrix = MatrixToImageWriterEx.createQRCode(codeNum, 150, 150);
		BufferedImage challenge = toBufferedImage(bitMatrix);
		ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
	    ImageIO.write(challenge, "png", imageStream);
	    byte[] tagInfo = imageStream.toByteArray();
	    String fileId = fileDubboService.uploadFile("VFS_RAINBOW_FILE_SERVER_DUBBO", tagInfo, "png", fileName, userId, false, true);
		return fileId;
	}
	
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	private TOriginIdentificationCodeExample convertExample(TOriginIdentificationCode condition) {
		TOriginIdentificationCodeExample example = new TOriginIdentificationCodeExample();
		Criteria cr = example.createCriteria();
		if(condition==null){
			return example;
		}
		if(StringUtils.isNotBlank(condition.getSkuName())){
			cr.andSkuNameLike("%"+condition.getSkuName()+"%");
		}
		if(StringUtils.isNotBlank(condition.getSkuBatch())){
			cr.andSkuBatchEqualTo(condition.getSkuBatch());
		}
		if(condition.getEnterpriseId()!=null){
			cr.andEnterpriseIdEqualTo(condition.getEnterpriseId());
		}
		if(condition.getSkuId()!=null){
			cr.andSkuIdEqualTo(condition.getSkuId());
		}
		if(StringUtils.isNotBlank(condition.getCodeType())){
			cr.andCodeTypeEqualTo(condition.getCodeType());
		}
		return example;
	}
	
	public String generationID(String codeType,String skuBarcode,String skuBatch){
		//标识码生成规则 类型+条形码+批次号+4位随机数
		Random random = new Random();  
		int s = random.nextInt(9000) + 1000; 
		String code = skuBarcode+skuBatch +s;
		if(codeType.equals(SpsyConstants.CODE_TYPE_SKU)){
			code = "S" + code;
		}else{
			code = "X" + code;
		}
		return code;
	}

	@Override
	public List<TOriginIdentificationCodeDetail> getCodeDetailList(TOriginIdentificationCode condition) throws BusinessException {
		List<TOriginIdentificationCodeDetail> codedetails = new ArrayList<TOriginIdentificationCodeDetail>();
		List<TOriginIdentificationCode> codelist = this.getCodeList(condition, -1, 0);
		if(codelist!=null && codelist.size()>0){
			List<Integer> buildIds = new ArrayList<Integer>();
			for(int i=0;i<codelist.size();i++){
				TOriginIdentificationCode code = codelist.get(i);
				buildIds.add(code.getBuildId());
			}
			TOriginIdentificationCodeDetailExample example = new TOriginIdentificationCodeDetailExample();
			example.createCriteria().andBuildIdIn(buildIds).andCodeStatusEqualTo(SpsyConstants.CODE_UN_USE);
			codedetails = originIdentificationCodeDetailDAO.selectByExample(example);
		}
		return codedetails;
	}

	@Override
	public void changeStatus(String codeNumber,String status) throws BusinessException {
		TOriginIdentificationCodeDetailExample example = new TOriginIdentificationCodeDetailExample();
		example.createCriteria().andCodeNumberEqualTo(codeNumber);
		TOriginIdentificationCodeDetail detail = new TOriginIdentificationCodeDetail();
		detail.setCodeStatus(status);
		originIdentificationCodeDetailDAO.updateByExampleSelective(detail, example);
	}

	@Override
	public String isDefinitionCode(Integer skuId) throws Exception {
		String flag = "0";
		TOriginIdentificationCode condition = new TOriginIdentificationCode();
		condition.setSkuId(skuId);
		TOriginIdentificationCodeExample example = convertExample(condition);
		List<TOriginIdentificationCode> codes = originIdentificationCodeDAO.selectByExample(example);
		if(codes!=null && codes.size()>0){
			flag = "1";
		}
		return flag;
	}

	@Override
	public String getCodeNumFirst(Integer buildId) throws BusinessException {
		String vfsId = "";
		TOriginIdentificationCodeDetailExample example = new TOriginIdentificationCodeDetailExample();
		example.createCriteria().andBuildIdEqualTo(buildId);
		List<TOriginIdentificationCodeDetail> codedetails = originIdentificationCodeDetailDAO.selectByExample(example);
		if(codedetails!=null && codedetails.size()>0){
			TOriginIdentificationCodeDetail detail = codedetails.get(0);
			vfsId = detail.getCodeVfsId();
		}
		return vfsId;
	}

	@Override
	public String createXml(Integer buildId,String codeType) throws Exception {
		TOriginIdentificationCodeDetailExample example = new TOriginIdentificationCodeDetailExample();
		example.createCriteria().andBuildIdEqualTo(buildId);
		List<TOriginIdentificationCodeDetail> codeDetails = originIdentificationCodeDetailDAO.selectByExample(example);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document doc = builder.newDocument();
        Element root = doc.createElement("code");  //创建根节点    
        Element eletype = doc.createElement("codetype");
        if(codeType!=null && codeType.equals(SpsyConstants.CODE_TYPE_SKU)){
        	eletype.appendChild(doc.createTextNode("产品标识码"));
	    }else{
	    	eletype.appendChild(doc.createTextNode("装箱标识码"));
	    }
        doc.appendChild(root);
        root.appendChild(eletype);        
        Element elelist = doc.createElement("codelist");       
        for(int i = 0; i < codeDetails.size(); i++){
			TOriginIdentificationCodeDetail detail = codeDetails.get(i);
			Element elecode = doc.createElement("codenum");
			elecode.appendChild(doc.createTextNode(detail.getCodeNumber()));
			elelist.appendChild(elecode);				
		}
        root.appendChild(elelist);     
        TransformerFactory   tf   =   TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        ByteArrayOutputStream   bos   =   new   ByteArrayOutputStream();
        t.transform(new DOMSource(doc), new StreamResult(bos));
        String xmlStr = bos.toString();
		return xmlStr;
	}
	
}
