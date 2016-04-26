package com.bdx.rainbow.service.basic.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;
import com.bdx.rainbow.entity.basic.mysql.SelfBasicLaw;
import com.bdx.rainbow.entity.basic.mysql.TBasicLawDbWithBLOBs;
import com.bdx.rainbow.mapper.basic.mysql.SelfLawMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicLawDbMapper;
import com.bdx.rainbow.service.basic.ILawService;

@Service("lawService")
@Transactional(rollbackFor=Exception.class,value="transactionManager")
public class LawService implements ILawService{
	
	@Autowired
	private SelfLawMapper selfLawMapper;
	
	@Autowired
	private TBasicLawDbMapper tBasicLawDbMapper;

	@Override
	public Map<String, Object> getNodeList(DubboLaw condition, Timestamp startTime,Timestamp endTime,int start, int limit)
			throws Exception {
		TBasicLawDbWithBLOBs basicCondition = new TBasicLawDbWithBLOBs();
		PropertyUtils.copyProperties(basicCondition, condition);
		Map<String, Object> paramMap = convertCondition(basicCondition, startTime, endTime);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DubboLaw> dLaws = new ArrayList<DubboLaw>();
		
		if (start >= 0 && limit > 0) {
			paramMap.put("limitClauseStart", start);
			paramMap.put("limitClauseCount", limit);
		}
		
		int total = selfLawMapper.countLaw(paramMap);
		resultMap.put("total", total);
		
		if (total == 0) {
			resultMap.put("list", dLaws);
			return resultMap;
		}
//		List<SelfBasicLaw> laws = selfLawMapper.getLawList(paramMap);
		List<SelfBasicLaw> laws = selfLawMapper.getNodeList(paramMap);
		DubboLaw dLaw = null;
		for (SelfBasicLaw law:laws) {
			dLaw = new DubboLaw();
			PropertyUtils.copyProperties(dLaw, law);
			dLaws.add(dLaw);
		}
		resultMap.put("list", dLaws);
		return resultMap;
	}

	@Override
	public DubboLaw getLawInfo(Integer lawId) throws Exception {
		TBasicLawDbWithBLOBs lawb = tBasicLawDbMapper.selectByPrimaryKey(lawId);
		// TODO Auto-generated method stub
		if (lawb == null) {
			return null;
		}else {
			DubboLaw dLaw = new DubboLaw();
			PropertyUtils.copyProperties(dLaw, lawb);
			return dLaw;
		}
	}
	
	private Map<String, Object> convertCondition(TBasicLawDbWithBLOBs condition,Timestamp startTime,Timestamp endTime){
//		SelfLawExample example = new SelfLawExample();
//		SelfLawExample.Criteria criteria = example.createCriteria();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (condition != null){
			if (condition.getLawId() != null) {
				paramMap.put("lawId", condition.getLawId());
			}
			//本条记录的类型  1：主标题 2：章 3：条 4：款 5:项 6：目 7:落款 8:节
			if (condition.getBttype() != null) {
//				criteria.andBusinesstypeEqualTo(condition.getBusinesstype());
				paramMap.put("bttype", condition.getBttype());
			}
			//业务类型：1、药品，2、医疗器械，3、餐饮食品，4、保健食品，5、化妆品，6、通用
			if (StringUtils.isNotEmpty(condition.getBusinesstype())) {
//				criteria.andBusinesstypeEqualTo(condition.getBusinesstype());
				paramMap.put("businesstype", condition.getBusinesstype());
			}
			//发布类型：1、法律，2、法规，3、司法解释，4、规章制度，5、规范性文件，6、其它
			if (StringUtils.isNotEmpty(condition.getPublishedtype())) {
//				criteria.andBusinesstypeEqualTo(condition.getBusinesstype());
				paramMap.put("publishedtype", condition.getPublishedtype());
			}
			
			if (StringUtils.isNotEmpty(condition.getIsdel())) {
//				criteria.andIsdelEqualTo(condition.getIsdel());
				paramMap.put("isdel", condition.getIsdel());
			}else {
//				criteria.andIsdelEqualTo("0");
				paramMap.put("isdel", "0");
			}
			if (StringUtils.isNotEmpty(condition.getTitle())) {
//				criteria.andTitleLike("%" + condition.getTitle() + "%");
				paramMap.put("title", "%" + condition.getTitle() + "%");
			}
			if (StringUtils.isNotEmpty(condition.getKeywords())) {
//				criteria.andKeywordsLike("%" + condition.getKeywords() + "%");
				paramMap.put("keywords", "%" + condition.getKeywords() + "%");
			}
			//添加发布机构
            if (StringUtils.isNotEmpty(condition.getAgencies())) {
//                criteria.andAgenciesLike("%" + condition.getAgencies() + "%");
            	paramMap.put("agencies", "%" + condition.getAgencies() + "%");
            }
            //添加内容
            if (StringUtils.isNotEmpty(condition.getContenttxt())) {
//            	criteria.andcon("%" + condition.getAgencies() + "%");
            	paramMap.put("contenttxt", "%" + condition.getContenttxt() + "%");
            }
            //添加发布类型
            if (StringUtils.isNotEmpty(condition.getPublishedtype())) {
//            	criteria.andPublishedtypeLike("%" + condition.getPublishedtype() + "%");
            	paramMap.put("publishedtype", "%" + condition.getPublishedtype() + "%");
            }
            //标题类型
            if (condition.getBttype() != null) {
//            	criteria.andBttypeEqualTo(condition.getBttype());
            	paramMap.put("bttype", condition.getBttype());
            }
            //添加发布的开始时间
            if (startTime != null) {
//            	criteria.andPublishTimeGreaterThanOrEqualTo(startTime);
            	paramMap.put("publishStartTime", startTime);
            }
            //添加发布的结束时间
            if (endTime != null) {
//                criteria.andPublishTimeLessThanOrEqualTo(endTime);
                paramMap.put("publishEndTime", endTime);
            }
            //添加父节id点条件
            if(condition.getParentid() != null){
//               criteria.andParentidEqualTo(condition.getParentid());
               paramMap.put("parentid", condition.getParentid());
            }
            
            if (StringUtils.isNotEmpty(condition.getRootid())) {
//				criteria.andRootidEqualTo(condition.getRootid());
//				if (condition.getParentid() == null) {
//					criteria.andParentidEqualTo(0);
//				}
            	paramMap.put("rootid", condition.getRootid());
            	if (condition.getParentid() == null) {
					paramMap.put("parentid", 0);
				}
			}
            
          //添加层级条件
            if (condition.getzIndex() != null) {
//				criteria.andZIndexEqualTo(condition.getzIndex());
//				if (condition.getzIndex() != 0) {
//					
//				}
            	if (condition.getzIndex() != 0 && condition.getParentid() == null ) {
            		paramMap.put("zIndex", condition.getzIndex());
					paramMap.put("childIndex", condition.getzIndex() + 1);
				}
            	if (condition.getzIndex() != 0 && condition.getParentid() != null ) {//子孩子列表下有多少个子子孩子
//            		paramMap.put("zIndex", condition.getzIndex());
					paramMap.put("childIndex", condition.getzIndex() + 1);
				}
            	
			}
			
		}
		return paramMap;
	}

	@Override
	public String getTitlesById(Integer lawId) throws Exception {
		TBasicLawDbWithBLOBs lawb = tBasicLawDbMapper.selectByPrimaryKey(lawId);
		// TODO Auto-generated method stub
		if (lawb == null) {
			return "";
		}else {
			String detailTitle = lawb.getRootid();//如： 0001000100060002
			String titles = "";
			if (StringUtils.isNotEmpty(detailTitle)) {
				List<String> rootids = getListByLength(detailTitle, 4);
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("list", rootids);
				titles = selfLawMapper.getTitles(param);
			}
			return titles;
		}
	}
	
	private static List<String> getListByLength(String src,int len) {
		List<String> srcArray = new ArrayList<String>();
		String tmp = null;
		for (int i = len; i <= src.length(); ) {
			tmp = src.substring(0,i);
//			System.out.println(tmp);
			srcArray.add(tmp);
			i += len;
		}
		return srcArray;
	}
	
	public static void main(String[] args) {
		getListByLength("0001000100010001",4);
	}

}
