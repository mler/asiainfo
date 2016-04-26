package com.bdx.rainbow.mapp.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;
import com.bdx.rainbow.mapp.model.bean.DubboLawMB;


public class TransformMapperBeanUtil {

	private static final Logger log = LoggerFactory
			.getLogger(TransformMapperBeanUtil.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object DTO2MB(Object sourceObj, Object targetObj) {
		if (sourceObj == null || targetObj == null) {
			return targetObj;
		}
		try {
			Class source = sourceObj.getClass();
			Class target = targetObj.getClass();
			Field[] sourceFields = source.getDeclaredFields();// 获得属性
			Field[] targetFields = target.getDeclaredFields();// 获得属性
			for (Field targetField : targetFields) {
				String targetFileName = targetField.getName();
				for (Field sourceField : sourceFields) {
					String sourceFieldName = sourceField.getName();
					PropertyDescriptor sourcePD;
					PropertyDescriptor targetPD ;
					try {
						sourcePD = new PropertyDescriptor(
							sourceFieldName, source);
					} catch (IntrospectionException e) {
					//bean命名不规范...
					sourcePD=new PropertyDescriptor(sourceFieldName,source,
					                "get"+sourceFieldName, "set"+sourceFieldName);
					}
					Method sourceGetMethod = sourcePD.getReadMethod();
					try {
					 targetPD = new PropertyDescriptor(
							 targetFileName, target);
					}
					catch (IntrospectionException e) {
						//bean命名不规范...
						targetPD=new PropertyDescriptor(targetFileName,target,
						                "get"+targetFileName, "set"+targetFileName);
					}
					// Method targetSetMethod=targetPD.getWriteMethod();
					Method targetGetMethod = targetPD.getReadMethod();
					// 1.属性名称相同赋值
					if (targetFileName.equals(sourceFieldName)) {
						targetField.setAccessible(true);
						targetField.set(targetObj,
								sourceGetMethod.invoke(sourceObj));

					}
					// 2. DBentiy to mappbean
					else if (targetFileName
							.equals(sourceFieldName.concat("MB"))) {
						Object targetGetObj = targetGetMethod.getReturnType()
								.newInstance();
						Object sourceGetObj = sourceGetMethod.invoke(sourceObj);
						if (sourceGetObj != null) {
							BeanUtils
									.copyProperties(sourceGetObj, targetGetObj);
							// targetSetMethod.invoke(targetObj,sourceGetObj);
							targetField.setAccessible(true);
							targetField.set(targetObj, targetGetObj);
						}

					}
					// 3.list<DBentiy> to list<mappbean>
					else if (sourceFieldName.lastIndexOf("List") > -1
							&& targetFileName.lastIndexOf("MBList") > -1) {
						if (sourceFieldName.substring(0,
								sourceFieldName.lastIndexOf("List")).equals(
								targetFileName.substring(0,
										targetFileName.lastIndexOf("MBList")))) {

							// Object
							// targetGetObj=targetField.getGenericType().getClass().newInstance();
							Object sourceGetObj = sourceGetMethod
									.invoke(sourceObj);
							if (sourceGetObj != null) {

								ParameterizedType pt = (ParameterizedType) targetField
										.getGenericType();

								targetField.getType().getGenericSuperclass();
					
								List sourceGetList = (ArrayList) sourceGetObj;
								List targetGetList = new ArrayList(sourceGetList.size());
								for (int i = 0; i < sourceGetList.size(); i++) {
									Object targetListObj = ((Class) pt
											.getActualTypeArguments()[0])
											.newInstance();
									BeanUtils
											.copyProperties(
													sourceGetList.get(i),
													targetListObj);
									targetGetList.add(targetListObj);
								}
								// targetSetMethod.invoke(targetObj,targetGetList);
								targetField.setAccessible(true);
								targetField.set(targetObj, targetGetList);
							}
						}
					}
				}

			}
		} catch (Exception e) {
			log.error("DTO2MB发生错误", e);
		}
		return targetObj;

	}

	@SuppressWarnings("unchecked")
	public static <E, T> List<E> DTOList2MBList(List<T> dtoList, List<E> mbList,
			Class<E> e) {
		if (dtoList == null || mbList == null) {
			return mbList;
		}
		try {
//			 Method	method = TransformMapperBeanUtil.class.getMethod("DTOList2MBList",List.class,List.class);
//			 ParameterizedType pt = (ParameterizedType)method.getGenericParameterTypes()[1];
//			 System.out.println(pt.getClass().getName());
//			 System.out.println(pt.getActualTypeArguments()[0]);
//			 System.out.println(pt.getActualTypeArguments()[0].getClass().getName());
		for (T t : dtoList) {
			 Object	obj = e.newInstance();
//				BeanUtils.copyProperties(t, obj);
				TransformMapperBeanUtil.DTO2MB(t, obj);
				mbList.add((E) obj);
		}
		}
		catch (Exception e1) {
			log.error("DTOList2MBList发生错误", e);
		}
		return mbList;

	}

	public static void main(String[] args) throws Exception {
		// YdzfMonitorPlanListInfoResultVO vo = new
		// YdzfMonitorPlanListInfoResultVO();
		// TYdzfMonitorPlan plan = new TYdzfMonitorPlan();
		// List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList = new
		// ArrayList<TYdzfMonitorTempletContent>();
		// TYdzfMonitorTemplet ydzfMonitorTemplet = new TYdzfMonitorTemplet();
		// plan.setCheckEnterpriseType("11111");
		// plan.setCheckPlanCode("2222");
		// TYdzfMonitorTempletContent c1= new TYdzfMonitorTempletContent();
		// c1.setItemRemark("11111");
		// c1.setItemContent("11111");
		// TYdzfMonitorTempletContent c2= new TYdzfMonitorTempletContent();
		// c2.setItemRemark("222222");
		// c2.setItemContent("2222");
		// ydzfMonitorTempletContentList.add(c1);
		// ydzfMonitorTempletContentList.add(c2);
		// vo.setYdzfMonitorPlan(plan);
		// vo.setYdzfMonitorTempletContentList(ydzfMonitorTempletContentList);
		// YDZF0006Response res= new YDZF0006Response();
		// TransformMapperBeanUtil.DTO2MB(vo, res);
		// System.out.println(res);
		// System.out.println(res.getYdzfMonitorTempletContentMBList().size());
		// System.out.println(res.getYdzfMonitorTempletContentMBList().get(0).getItemRemark());
		// System.out.println(res.getYdzfMonitorTempletContentMBList().get(1).getItemRemark());
//		List<YdzfMonitorPlanListResultVO> ydzfMonitorPlanListResultVOList = new ArrayList<YdzfMonitorPlanListResultVO>();
//		YdzfMonitorPlanListResultVO vo = new YdzfMonitorPlanListResultVO();
//		vo.setCheckPlanCode("11111");
//		YdzfMonitorPlanListResultVO vo2 = new YdzfMonitorPlanListResultVO();
//		vo2.setCheckPlanCode("222");
//		ydzfMonitorPlanListResultVOList.add(vo);
//		ydzfMonitorPlanListResultVOList.add(vo2);
//		List<YdzfMonitorPlanListResultVOMapperBean> ydzfMonitorPlanListResultVOMapperBeanList = new ArrayList<YdzfMonitorPlanListResultVOMapperBean>(
//				ydzfMonitorPlanListResultVOList.size());
//		TransformMapperBeanUtil.DTOList2MBList(ydzfMonitorPlanListResultVOList,
//				ydzfMonitorPlanListResultVOMapperBeanList,
//				YdzfMonitorPlanListResultVOMapperBean.class);
//		for (YdzfMonitorPlanListResultVOMapperBean mb : ydzfMonitorPlanListResultVOMapperBeanList) {
//			System.out.println(mb.getCheckPlanCode());
//		}
//		YdzfMonitorPlanListResultVO vo = new YdzfMonitorPlanListResultVO();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("monitorPlanId", 11);
//		map.put("checkPlanType", 22);
//		
//		org.apache.commons.beanutils.BeanUtils.populate(vo, map);
//		System.out.println(vo.getMonitorPlanId());
//		System.out.println(vo.getCheckPlanType());
		DubboLaw law = new DubboLaw();
		DubboLawMB mb = new DubboLawMB();
		law.setAgencies("1");
		law.setzIndex(2);
		TransformMapperBeanUtil.DTO2MB(law, mb);
		System.out.println(mb.getAgencies());
		System.out.println(mb.getzIndex());
		

	}

}
