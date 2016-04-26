package com.bdx.rainbow.service.ydzf.monitor.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.core.utils.CalculateUtil;
import com.bdx.rainbow.core.utils.StringMyUtils;
import com.bdx.rainbow.dto.ydzf.form.YdzfMonitorPlanAddForm;
import com.bdx.rainbow.dto.ydzf.vo.*;
import com.bdx.rainbow.entity.ydzf.*;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanRelMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorTaskInfoMapper;
import com.bdx.rainbow.mapper.ydzf.self.TYdzfMonitorPlanSelfMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.common.IYDZFParamDetailService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanRelService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorTaskService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFMonitorTempletContentService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFMonitorTempletService;
import com.bdx.rainbow.urs.entity.IUserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ydzfMonitorPlanService")
public class YDZFMonitorPlanService extends BaseService implements
		IYDZFMonitorPlanService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFMonitorPlanService.class);

	@Autowired
	protected TYdzfMonitorPlanMapper ydzfMonitorPlanMapper;
	@Autowired
	protected TYdzfMonitorPlanRelMapper ydzfMonitorPlanRelMapper;
	@Autowired
	protected TYdzfMonitorTaskInfoMapper ydzfMonitorTaskInfoMapper;
	@Autowired
	protected TYdzfMonitorPlanSelfMapper ydzfMonitorPlanSelfMapper;
	@Autowired
	protected IYDZFMonitorTempletContentService ydzfMonitorTempletContentService;
	@Autowired
	protected IYDZFMonitorTempletService ydzfMonitorTempletService;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Autowired
    protected IYDZFMonitorPlanRelService ydzfMonitorPlanRelService;
	@Autowired
    protected IYDZFMonitorTaskService ydzfMonitorTaskService;
    @Autowired
    protected IYDZFParamDetailService ydzfParamDetailService;


	@Override
	public List<YdzfMonitorPlanListNumResultVO> queryPlanListNum(int deptId,
			String checkPlanType, String checkPlanStatus)
			throws SystemException, BusinessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		paramMap.put("checkPlanType", checkPlanType);
		paramMap.put("checkPlanStatus", checkPlanStatus);
		return ydzfMonitorPlanSelfMapper.queryPlanListNum(paramMap);
	}

    @Override
    public int updateMonitorPlan(YdzfMonitorPlanAddForm ydzfMonitorPlanForm) throws BusinessException, SystemException {
        TYdzfMonitorPlan plan = ydzfMonitorPlanForm.getYdzfMonitorPlan();
        ydzfMonitorPlanMapper.updateByPrimaryKeySelective(plan);
        if(ydzfMonitorPlanForm.getEnterpriseIdList()!=null){
        for (Integer enterpriseId : ydzfMonitorPlanForm.getEnterpriseIdList()) {
            TYdzfMonitorPlanRel planRel = new TYdzfMonitorPlanRel();
            planRel.setManageEnterpriseId(enterpriseId);
            planRel.setUpdateDate(plan.getUpdateDate());
            planRel.setUpdateUserid(plan.getUpdateUserid());
            planRel.setMonitorPlanId(plan.getMonitorPlanId());
            ydzfMonitorPlanRelMapper.updateByPrimaryKeySelective(planRel);
        }
            TYdzfMonitorTaskInfo taskInfo = new TYdzfMonitorTaskInfo();
            taskInfo.setUpdateDate(plan.getUpdateDate());
            taskInfo.setUpdateUserid(plan.getUpdateUserid());
            ydzfMonitorTaskInfoMapper.updateByPrimaryKeySelective(taskInfo);
        }


        return 0;
    }

    @Override
	@Transactional
	public int addMonitorPlan(YdzfMonitorPlanAddForm ydzfMonitorPlanForm)
			throws BusinessException, SystemException {
		TYdzfMonitorPlan plan = ydzfMonitorPlanForm.getYdzfMonitorPlan();
		ydzfMonitorPlanMapper.insertSelective(plan);
		// 监督管理关联表
		for (Integer enterpriseId : ydzfMonitorPlanForm.getEnterpriseIdList()) {
			TYdzfMonitorPlanRel planRel = new TYdzfMonitorPlanRel();
			planRel.setManageEnterpriseId(enterpriseId);
			planRel.setCreateDate(plan.getCreateDate());
			planRel.setCreateUserid(plan.getCreateUserid());
			planRel.setManageItemStatus(YDZFConstants.MONITOR.PLAN_REL_STATUS_EXECUTE_WAIT);
			planRel.setMonitorPlanId(plan.getMonitorPlanId());
			planRel.setRemark("rel-remark");
			ydzfMonitorPlanRelMapper.insertSelective(planRel);
			// 插入监督管理任务详情表
			TYdzfMonitorTaskInfo taskInfo = new TYdzfMonitorTaskInfo();
			taskInfo.setMonitorPlanId(plan.getMonitorPlanId());
			taskInfo.setMonitorPlanRelId(planRel.getMonitorPlanRelId());
			taskInfo.setCheckTaskStatus(YDZFConstants.MONITOR.TASK_STATUS_EXECUTE_INIT);
			taskInfo.setCreateDate(plan.getCreateDate());
			taskInfo.setCreateUserid(plan.getCreateUserid());
			ydzfMonitorTaskInfoMapper.insertSelective(taskInfo);
		}
		return plan.getMonitorPlanId();
	}

	@Override
	public List<YdzfMonitorPlanListResultVO> queryMonitorPlanList(
			TYdzfMonitorPlan ydzfMonitorPlan,Integer start,
			Integer limit) throws BusinessException, SystemException {
		List<YdzfMonitorPlanListResultVO> voList = new ArrayList<YdzfMonitorPlanListResultVO>();
		TYdzfMonitorPlanExample example = this.getCondition(ydzfMonitorPlan);
		if (start!=null&&start >= 0 && limit!=null&&limit > 0) {
			example.setLimitClauseStart(start);
			example.setLimitClauseCount(limit);
		}
		example.setOrderByClause(" CREATE_DATE desc ");
		List<TYdzfMonitorPlan> planList = ydzfMonitorPlanMapper
				.selectByExample(example);
		for (TYdzfMonitorPlan plan : planList) {
			YdzfMonitorPlanListResultVO vo = new YdzfMonitorPlanListResultVO();
			BeanUtils.copyProperties(plan, vo);
			vo.setPlanCountTotal(planList.size());
			switch (plan.getCheckPlanStatus()) {
			case YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_WAIT:
				vo.setPlanCountFinish(0);
				vo.setPlanCountPercent(CalculateUtil.getPercent(
						vo.getPlanCountFinish(), vo.getPlanCountFinish()));
				break;
			// 如果是正在执行中，需要计算完成百分比
			case YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_ING:
				//
				TYdzfMonitorPlanRelExample relExample = new TYdzfMonitorPlanRelExample();
				com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanRelExample.Criteria relCr = relExample
						.createCriteria();
				relCr.andMonitorPlanIdEqualTo(plan.getMonitorPlanId());
				relCr.andManageItemStatusEqualTo(YDZFConstants.MONITOR.PLAN_REL_STATUS_EXECUTE_FINISH);
				int planCountFinish = ydzfMonitorPlanRelMapper
						.countByExample(relExample);
				vo.setPlanCountFinish(planCountFinish);
				vo.setPlanCountPercent(CalculateUtil.getPercent(
						vo.getPlanCountFinish(), vo.getPlanCountTotal()));
				break;

			case YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_FINISH:
				vo.setPlanCountFinish(planList.size());
				vo.setPlanCountPercent(CalculateUtil.getPercent(
						vo.getPlanCountFinish(), vo.getPlanCountFinish()));
				break;
			default:
				throw new BusinessException(new DefaultExceptionCode("999999",
						"任务状态异常"));

			}
			voList.add(vo);
		}

		return voList;

	}
	@Override
	public int countMonitorPlanList(TYdzfMonitorPlan ydzfMonitorPlan)
			throws BusinessException, SystemException {
		TYdzfMonitorPlanExample example = this.getCondition(ydzfMonitorPlan);
		return ydzfMonitorPlanMapper.countByExample(example);
				
	}

	@Override
	public YdzfMonitorPlanListInfoResultVO queryMonitorPlanListInfo(
			Integer monitorPlanId, String checkPlanType) throws BusinessException,
			SystemException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("monitorPlanId", monitorPlanId);
		paramMap.put("checkPlanType", checkPlanType);
		YdzfMonitorPlanListInfoResultVO vo = ydzfMonitorPlanSelfMapper
				.queryMonitorPlanListInfo(paramMap);
		if (vo != null) {
			
			//TODO:填充任务制定人信息
			//填充企业信息
			List<YdzfMonitorPlanRelEnterpriseVO> enterpriseVOList=vo.getEnterpriseAllVOList();
			if(!CollectionUtils.isEmpty(enterpriseVOList))
			{
				for(YdzfMonitorPlanRelEnterpriseVO enterpriseVO :enterpriseVOList)
				{
					YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO=ydzfCommonService.getEnterpriseInfoByIdDubbo(enterpriseVO.getManageEnterpriseId());
					if(ydzfEnterpriseInfoVO!=null)
					{
						enterpriseVO.setLegalPerson(ydzfEnterpriseInfoVO.getLegalPerson());
						enterpriseVO.setLegalPersonPhone(ydzfEnterpriseInfoVO.getLegalPersonPhone());
						enterpriseVO.setManageEnterpriseName(ydzfEnterpriseInfoVO.getManageEnterpriseName());
						enterpriseVO.setRegiAddress(ydzfEnterpriseInfoVO.getRegiAddress());
						enterpriseVO.setCheckEnterpriseType(ydzfEnterpriseInfoVO.getBusinessType());

					}
					try {
						if(enterpriseVO.getCheckTaskUserId()!=null)
						{
							enterpriseVO.setCheckTaskUserName(ydzfParamDetailService.getParamName("SYS_USER_NAME", enterpriseVO.getCheckTaskUserId().toString(),"","urs"));
						}
					} catch (Exception e1) {
						log.error("获取检查人姓名异常", e1);
						throw new SystemException(new DefaultExceptionCode("获取检查人姓名异常"));
					}
					try {
						List<String> fileIdList = StringMyUtils.transArrayToListString(
                                enterpriseVO
                                        .getFileIds(), ",");
						if (fileIdList != null) {
							enterpriseVO.setFileidHttpUrlMap(ydzfCommonService
									.getFileHttpUrlsDubbo(fileIdList));
						}
					} catch (Exception e) {
						log.error("获取文件异常", e);
						throw new SystemException(new DefaultExceptionCode("获取文件异常"));
					}
					try {
						List<String> checkSignFileIdList = StringMyUtils.transArrayToListString(
								enterpriseVO
										.getCheckSignFileIds(), ",");
						if (checkSignFileIdList != null) {
							enterpriseVO.setCheckSignFileIdsHttpUrlMap(ydzfCommonService
                                    .getFileHttpUrlsDubbo(checkSignFileIdList));
						}
					} catch (Exception e) {
						log.error("获取文件异常", e);
						throw new SystemException(new DefaultExceptionCode("获取文件异常"));
					}

					try {
						List<String> enterpriseSignFileIdList = StringMyUtils.transArrayToListString(
								enterpriseVO
										.getEnterpriseSignFileIds(), ",");
						if (enterpriseSignFileIdList != null) {
							enterpriseVO.setEnterpriseSignFileIdsHttpUrlMap(ydzfCommonService
									.getFileHttpUrlsDubbo(enterpriseSignFileIdList));
						}
					} catch (Exception e) {
						log.error("获取文件异常", e);
						throw new SystemException(new DefaultExceptionCode("获取文件异常"));
					}
				}
			}

			//填充模版内容
			int monitorTempletId = vo.getYdzfMonitorPlan()
					.getMonitorTempletId();
			TYdzfMonitorTemplet ydzfMonitorTemplet = ydzfMonitorTempletService
					.queryMonitorTempleteInfo(monitorTempletId);
			TYdzfMonitorTempletContent queryTempletContent = new TYdzfMonitorTempletContent();
			List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList = new ArrayList<TYdzfMonitorTempletContent>();
			queryTempletContent.setMonitorTempletId(monitorTempletId);
			ydzfMonitorTempletContentList = ydzfMonitorTempletContentService
					.queryMonitorTempleteContenAll(queryTempletContent);
			vo.setYdzfMonitorTemplet(ydzfMonitorTemplet);
			vo.setYdzfMonitorTempletContentList(ydzfMonitorTempletContentList);
		}

		return vo;
	}

//    @Override
//    public List<TYdzfMonitorPlan> queryMonitorPlan(TYdzfMonitorPlan obj, Integer start, Integer count)throws BusinessException,SystemException     {
//    TYdzfMonitorPlanExample example=getCondition(obj);
//        if (start!=null){
//            example.setLimitClauseStart(start);
//            example.setLimitClauseCount(count);
//        }
//        return ydzfMonitorPlanMapper.selectByExample(example);
//
//
//    }
	
	@Override
	@Transactional
	public int delMonitor(IUserInfo userInfo, List<Integer> monitorPlanIds)
			throws BusinessException, SystemException {
		//关联删除计划企业表，计划任务表
		TYdzfMonitorPlan ydzfMonitorPlan= new TYdzfMonitorPlan();
		Timestamp updateDate=DateUtil.getCurrent();
		ydzfMonitorPlan.setUpdateDate(updateDate);
		ydzfMonitorPlan.setUpdateUserid(userInfo.getUserId());
		ydzfMonitorPlan.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		if(!CollectionUtils.isEmpty(monitorPlanIds))
		{
			for(Integer monitorPlanId:monitorPlanIds)
			{
				ydzfMonitorPlan.setMonitorPlanId(monitorPlanId);
				ydzfMonitorPlanMapper.updateByPrimaryKeySelective(ydzfMonitorPlan);
				ydzfMonitorPlanRelService.delMonitorPlanRelByPlanId(userInfo, monitorPlanId);
				ydzfMonitorTaskService.delMonitorPlanTaskByPlanId(userInfo, monitorPlanId);
			}
		}
	
		return 0;
	}

   
    private TYdzfMonitorPlanExample getCondition(TYdzfMonitorPlan record) {
    	TYdzfMonitorPlanExample example=new TYdzfMonitorPlanExample();
    		if (record != null) {
    	com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanExample.Criteria cr=example.createCriteria();
    	if(record.getMonitorPlanId()!= null && record.getMonitorPlanId()>0) {
    	cr.andMonitorPlanIdEqualTo(record.getMonitorPlanId());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCheckPlanType())){
    	cr.andCheckPlanTypeEqualTo(record.getCheckPlanType());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCheckPlanCode())){
    	cr.andCheckPlanCodeEqualTo(record.getCheckPlanCode());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCheckPlanName())){
    	cr.andCheckPlanNameEqualTo(record.getCheckPlanName());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCheckPlanContent())){
    	cr.andCheckPlanContentEqualTo(record.getCheckPlanContent());
    	}
    	if(record.getMonitorTempletId()!= null && record.getMonitorTempletId()>0) {
    	cr.andMonitorTempletIdEqualTo(record.getMonitorTempletId());
    	}
    	if(record.getCheckPlanCreatDepId()!= null && record.getCheckPlanCreatDepId()>0) {
    	cr.andCheckPlanCreatDepIdEqualTo(record.getCheckPlanCreatDepId());
    	}
    	if(record.getCheckPlanCreatUserCode()!= null && record.getCheckPlanCreatUserCode()>0) {
    	cr.andCheckPlanCreatUserCodeEqualTo(record.getCheckPlanCreatUserCode());
    	}
    	if(record.getCheckPlanCreatCreatDate()!= null){
    	cr.andCheckPlanCreatCreatDateEqualTo(record.getCheckPlanCreatCreatDate());
    	}
    	if(record.getCheckPlanFinishDate()!= null){
    	cr.andCheckPlanFinishDateEqualTo(record.getCheckPlanFinishDate());
    	}
    	if(record.getCheckPlanBeginDate()!= null){
    	cr.andCheckPlanBeginDateGreaterThanOrEqualTo(record.getCheckPlanBeginDate());
    	}
    	if(record.getCheckPlanEndDate()!= null){
    	cr.andCheckPlanEndDateLessThanOrEqualTo(record.getCheckPlanEndDate());
    	}
    	if(record.getCheckExecuteDepId()!= null && record.getCheckExecuteDepId()>0) {
    	cr.andCheckExecuteDepIdEqualTo(record.getCheckExecuteDepId());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCheckEnterpriseType())){
    	cr.andCheckEnterpriseTypeEqualTo(record.getCheckEnterpriseType());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getProvince())){
    	cr.andProvinceEqualTo(record.getProvince());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getAreaid())){
    	cr.andAreaidEqualTo(record.getAreaid());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCountyid())){
    	cr.andCountyidEqualTo(record.getCountyid());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCheckRange())){
    	cr.andCheckRangeEqualTo(record.getCheckRange());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getCheckPlanStatus())){
    	cr.andCheckPlanStatusEqualTo(record.getCheckPlanStatus());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getRemark())){
    	cr.andRemarkEqualTo(record.getRemark());
    	}
    	if(org.apache.commons.lang.StringUtils.isNotBlank(record.getIsDel())){
    	cr.andIsDelEqualTo(record.getIsDel());
    	}
    	if(record.getCreateDate()!= null){
    	cr.andCreateDateEqualTo(record.getCreateDate());
    	}
    	if(record.getCreateUserid()!= null && record.getCreateUserid()>0) {
    	cr.andCreateUseridEqualTo(record.getCreateUserid());
    	}
    	if(record.getUpdateDate()!= null){
    	cr.andUpdateDateEqualTo(record.getUpdateDate());
    	}
    	if(record.getUpdateUserid()!= null && record.getUpdateUserid()>0) {
    	cr.andUpdateUseridEqualTo(record.getUpdateUserid());
    	}
    		}
    		return example;
    	}





	



}
