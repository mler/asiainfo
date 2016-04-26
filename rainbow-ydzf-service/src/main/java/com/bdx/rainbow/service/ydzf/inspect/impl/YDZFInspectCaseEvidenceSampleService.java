package com.bdx.rainbow.service.ydzf.inspect.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.core.utils.StringMyUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceSampleResultInfoVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSampleExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseEvidenceMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseEvidenceSampleMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceSampleService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.urs.entity.IUserInfo;

@Service("ydzfInspectCaseEvidenceSampleService")
public class YDZFInspectCaseEvidenceSampleService extends BaseService implements
		IYDZFInspectCaseEvidenceSampleService {

	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseEvidenceSampleService.class);
	@Autowired
	protected TYdzfInspectCaseEvidenceSampleMapper ydzfInspectCaseEvidenceSampleMapper;
	@Autowired
	protected TYdzfInspectCaseEvidenceMapper ydzfInspectCaseEvidenceMapper;
	@Autowired
	protected IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;

	@Override
	@Transactional
	public int addInspectCaseEvidenceSample(
			TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample)
			throws BusinessException, SystemException {
		ydzfInspectCaseEvidenceSampleMapper
				.insert(ydzfInspectCaseEvidenceSample);
		return ydzfInspectCaseEvidenceSample.getInspectCaseEvidenceSampleId();
	}

	@Override
	@Transactional
	public int updateInspectCaseEvidenceSample(
			TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample)
			throws BusinessException, SystemException {
		ydzfInspectCaseEvidenceSampleMapper
				.updateByPrimaryKeySelective(ydzfInspectCaseEvidenceSample);
		return ydzfInspectCaseEvidenceSample.getInspectCaseEvidenceSampleId();
	}

	@Override
	@Transactional
	public int delInspectCaseEvidenceSample(
			Integer inspectCaseEvidenceSampleId, IUserInfo userInfo)
			throws BusinessException, SystemException {
		TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample = new TYdzfInspectCaseEvidenceSample();
		ydzfInspectCaseEvidenceSample.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		ydzfInspectCaseEvidenceSample
				.setInspectCaseEvidenceSampleId(inspectCaseEvidenceSampleId);
		ydzfInspectCaseEvidenceSample.setUpdateDate(DateUtil.getCurrent());
		ydzfInspectCaseEvidenceSample.setUpdateUserid(userInfo.getUserId());
		ydzfInspectCaseEvidenceSampleMapper
				.updateByPrimaryKeySelective(ydzfInspectCaseEvidenceSample);
		try {
			// 删除相关的附件
			List<String> fileIdList = StringMyUtils.transArrayToListString(
					ydzfInspectCaseEvidenceSample.getSampleFileIds(), ",");
			ydzfCommonService
					.removeFilesDubbo(fileIdList, userInfo.getUserId());
		} catch (Exception e) {
			log.error("删除文件出错", e);
		}
		return inspectCaseEvidenceSampleId;
	}

	@Override
	public YdzfInspectCaseEvidenceSampleResultInfoVO queryInspectCaseEvidenceSampleInfoById(
			Integer inspectCaseEvidenceSampleId) throws BusinessException,
			SystemException {
		YdzfInspectCaseEvidenceSampleResultInfoVO resultVO = new YdzfInspectCaseEvidenceSampleResultInfoVO();
		TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample = ydzfInspectCaseEvidenceSampleMapper
				.selectByPrimaryKey(inspectCaseEvidenceSampleId);
		resultVO.setYdzfInspectCaseEvidenceSample(ydzfInspectCaseEvidenceSample);
		try {
			List<String> fileIdList = StringMyUtils.transArrayToListString(
					ydzfInspectCaseEvidenceSample.getSampleFileIds(), ",");
			if (fileIdList != null) {
				resultVO.setSampleFileidHttpUrlMap(ydzfCommonService
						.getFileHttpUrlsDubbo(fileIdList));
			}
		} catch (Exception e) {
			log.error("获取文件异常", e);
			throw new SystemException(new DefaultExceptionCode("获取文件异常"));
		}
		return resultVO;
	}

	@Override
	public List<TYdzfInspectCaseEvidenceSample> queryInspectCaseEvidenceSampleListAll(
			Integer inspectCaseEvidenceId) throws BusinessException,
			SystemException {
		List<TYdzfInspectCaseEvidenceSample> resultList = new ArrayList<TYdzfInspectCaseEvidenceSample>();
		TYdzfInspectCaseEvidence ydzfInspectCaseEvidence = ydzfInspectCaseEvidenceMapper
				.selectByPrimaryKey(inspectCaseEvidenceId);
		if (ydzfInspectCaseEvidence == null) {
			throw new BusinessException(
					new DefaultExceptionCode("调查取证信息不存在不存在"));
		}
		String sampleIds = ydzfInspectCaseEvidence
				.getInspectCaseEvidenceSampleIds();
		resultList = this.queryInspectCaseEvidenceSampleListAll(StringMyUtils
				.transArrayToListInteger(sampleIds, ","));
		return resultList;

	}

	@Override
	public List<TYdzfInspectCaseEvidenceSample> queryInspectCaseEvidenceSampleListAll(
			List<Integer> inspectCaseEvidenceSampleIdList)
			throws BusinessException, SystemException {
		if (CollectionUtils.isEmpty(inspectCaseEvidenceSampleIdList)) {
			throw new BusinessException(new DefaultExceptionCode("参数为空"));
		}
		TYdzfInspectCaseEvidenceSampleExample example = new TYdzfInspectCaseEvidenceSampleExample();
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSampleExample.Criteria cr = example
				.createCriteria();
		cr.andInspectCaseEvidenceSampleIdIn(inspectCaseEvidenceSampleIdList);
		example.setOrderByClause(" Inspect_CASE_EVIDENCE_SAMPLE_ID asc  ");
		return ydzfInspectCaseEvidenceSampleMapper.selectByExample(example);
	}

}
