package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.SK0006Request;
import com.bdx.rainbow.mapp.model.rsp.SK0006Response;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/*
 * 日常检查列表
 */
@Service("sk0006")
@Action(bizcode="sk0006", version = "1.0", usercheck=false, ipcheck=false)
public class SK0006Action extends AbstractMappAction<SK0006Request, SK0006Response> {
//    @Autowired
//    private IDailyCheckSchemeService dailyCheckSchemeService;
//    @Autowired
//    private IDailyCheckInfoManageService infoManageService;
	@Override
	public void doAction(SK0006Request request, SK0006Response response) throws Exception {
//		Map param=new HashMap();
//		if (StringUtils.isNotBlank(this.request.getPageCount())) {
//            param.put("pageSize", this.request.getPageCount());
//        }
//        if (StringUtils.isNotBlank(this.request.getPageNumber())) {
//            param.put("page",Integer.valueOf(this.request.getPageNumber())+1);
//        }
//        if (StringUtils.isNotBlank(this.request.getCheckSTime())) {
//            param.put("startTime", this.request.getCheckSTime());
//        }
//        if (StringUtils.isNotBlank(this.request.getCheckETime())) {
//            param.put("endTime",this.request.getCheckETime());
//        }
//        SysUser user=(SysUser) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
//
////        param.put("checkLevel", 1);
//        param.put("comid",user.getCorpId());
//        param.put("orgIds","");
//        List list=infoManageService.getList(param).getRows();
////        if("0".equals(this.request.getPageCount())&&"-1".equals(this.request.getPageNumber())){
////           	list =dailyCheckSchemeService.selectScheme(param);
////        }else{
////            list =dailyCheckSchemeService.selectListScheme(param).getRows();
////        }
//        if(list!=null&&list.size()>0){
//            List<SK0006Response.Check> checks=new ArrayList<SK0006Response.Check>();
//            for (int i = 0; i <list.size() ; i++) {
//                Map map =(Map) list.get(i);
//                SK0006Response.Check check=new SK0006Response.Check();
//                check.setId(String.valueOf(map.get("manageid")));//检查id
//                check.setName(map.get("schemeName").toString());//检查方案名称
//                if(0==Integer.valueOf(map.get("resultCount").toString())&&Integer.valueOf(map.get("checkCountersignCount").toString())==0){
//                    check.setResult("等待检查");
//                }else if(0<Integer.valueOf(map.get("resultCount").toString())&&Integer.valueOf(map.get("checkCountersignCount").toString())==0){
//                    check.setResult("已检查");
//                }
//                if(0<Integer.valueOf(map.get("resultCount").toString())&&0<Integer.valueOf(map.get("checkCountersignCount").toString())&&Integer.valueOf(map.get("checkCountersignCount").toString())<2){
//                    check.setResult("等待会签");
//                }else if(0<Integer.valueOf(map.get("resultCount").toString())&&Integer.valueOf(map.get("checkCountersignCount").toString())>=2){
//                    check.setResult("会签已结束");
//                }
//                if(0<Integer.valueOf(map.get("resultCount").toString())&&Integer.valueOf(map.get("checkCountersignCount").toString())>=2&&Integer.valueOf(map.get("isMajorAccident").toString())==2){
//                    if(Integer.valueOf(map.get("isInspect").toString())==0){
//                        check.setResult("等待转入稽查");
//                    }else if(Integer.valueOf(map.get("isInspect").toString())==1){
//                        check.setResult("已转入稽查");
//                    }
//
//                }//审核方案结果
//                check.setOrgId(String.valueOf(map.get("orgId")));//执法单位(id),#要查字典
//                check.setCreateTime(map.get("createTime").toString());//检查日期
//
//                checks.add(check);
//            }
//            this.response.setCheckList(checks);
//        }
        
	}
}
        
       



