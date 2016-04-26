package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.SK0007Request;
import com.bdx.rainbow.mapp.model.rsp.SK0007Response;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/*
 * 日常检查详情
 */
@Service("sk0007")
@Action(bizcode="sk0007",version = "1.0", usercheck=false, ipcheck=false)
public class SK0007Action extends AbstractMappAction<SK0007Request, SK0007Response> {
//	 @Autowired
//	    private IDailyCheckSchemeService dailyCheckSchemeService;
//    @Autowired
//	    private IDailyCheckTypeItemService dailyCheckTypeItemService;
//    @Autowired
//    private IDailyCheckInfoManageService infoManageService;
	@Override
	public void doAction(SK0007Request request, SK0007Response response) throws Exception {
//        DailyCheckInfoManage manage = infoManageService.load(Integer.parseInt(this.request.getId()));
//        DailyCheckScheme scheme = manage.getCheckTask().getCheckScheme();
////		DailyCheckScheme dailyCheckScheme=new DailyCheckScheme();
////		dailyCheckScheme=dailyCheckSchemeService.load(Integer.parseInt(this.request.getId()));
//		this.response.setName(scheme.getName());//方案名称
//		this.response.setResult(String.valueOf(scheme.getAuditFlow()));//审核是否通过
//		this.response.setOrgId(String.valueOf(scheme.getOrgId()));//执法单位
//		this.response.setCheckStartTime(scheme.getStartTime());//开始时间
//		this.response.setCheckEndTime(scheme.getEndTime());//结束时间
//		List list=new ArrayList();//list为检查子项列表
//        if (StringUtils.isNotBlank(scheme.getCheckTypeIDS())){
//            try{
//                list=dailyCheckTypeItemService.getItemByTypeAndManage(scheme.getCheckTypeIDS(), Integer.parseInt(this.request.getId()));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            if(list!=null){
//                List <SK0007Response.Content> contents=new ArrayList<SK0007Response.Content>();
//                for (int i = 0; i <list.size() ; i++) {
//                    Map map =(Map) list.get(i);
//            /*
//             * 检查子项列表
//             */
//                    SK0007Response.Content content=new SK0007Response.Content();
//                    content.setItemName(map.get("itemName").toString());//检查内容
//                    content.setManner(map.get("manner").toString()); //检查方式
//                    switch (Integer.valueOf(map.get("isLacuna").toString())){
//                        case 1: content.setIsLacuna("是");break;//合理性缺项
//                        case 0: content.setIsLacuna("否");break;//合理性缺项
//                    }
//
//                    switch (Integer.valueOf(map.get("significance").toString())){
//                        case 1:content.setSignificance("*");break;//重要性
//                        case 2:content.setSignificance("**");break;//重要性
//                        case 3:content.setSignificance("***");break;//重要性
//                    }
//
//                    content.setScore(map.get("resultScore").toString()+"/"+map.get("score").toString());//分值
//                    switch (Integer.valueOf(map.get("result").toString())){
//                        case 1:if(Integer.valueOf(map.get("checkResult").toString())==1)content.setResult("是");else content.setResult("否");break;//检查结果
//                        case 2:if(Integer.valueOf(map.get("checkResult").toString())==1)content.setResult("有");else content.setResult("无");break;//检查结果
//                        case 3:if(Integer.valueOf(map.get("checkResult").toString())==1)content.setResult("符合规定");else content.setResult("不符合规定");break;//检查结果
//                        case 4:if(Integer.valueOf(map.get("checkResult").toString())==1)content.setResult("发现问题");else content.setResult("未发现问题");break;//检查结果
//                        case 5:content.setResult("手动输入");break;//检查结果
//                        case 6:content.setResult("其他");break;//检查结果
//                    }
////                    content.setResult(String.valueOf(map.getResult()));//
//                    content.setRemark(map.get("remark").toString());//说明						        //检查结果
//                    contents.add(content);
//                }
//                this.response.setContents(contents);
//            }
//        }

	}

}
