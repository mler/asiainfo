package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.SysDept;
import com.bdx.rainbow.entity.urs.SysDeptExt;
import com.bdx.rainbow.entity.urs.SysDeptExtExample;
import com.bdx.rainbow.entity.urs.SysDeptHis;
import com.bdx.rainbow.urs.common.SequenceUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl extends AbstractDeptServiceImpl {

    public Integer insertDept(SysDept record) throws SystemException, BusinessException {
        record.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if( sysDeptMapper.insertSelective(record)>0){
        
//            SysDept dept=  deptService.getDepts(record, null, null).get(0);
//            SysUser newuser= new SysUser();
//            newuser.setUserName(dept.getAdmin());
//            SysUser user=userService.getUsers(newuser,null,null).get(0);
//            userService.updateUser(user);
        }
        return  null;
    }
    public void insertDeptHis(SysDeptHis record)throws SystemException, BusinessException{
        record.setCreateTime(new Timestamp(System.currentTimeMillis()));
        sysDeptHisMapper.insertSelective(record);
    }
	@Override
	public int updateDept(SysDept record, List<SysDeptExt> recordExts) throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("deptId", record.getDeptId());
		condition.put("bussin_type", "E");
		sysPowerCommMapper.insertDeptHis(condition);
		int count = this.updateDept(record);
		if (recordExts != null) {
			for (SysDeptExt cext : recordExts) {
				cext.setDeptId(record.getDeptId());
				
				if (this.countDeptExtByIdAndKey(record.getDeptId(), cext.getExtKey()) == 0) {
					this.insertDeptExt(cext);
				} else {
					condition.put("key", cext.getExtKey());
					sysPowerCommMapper.insertDeptExtHis(condition);
					this.updateDeptExt(cext);
				}
			}
		} else {
			sysPowerCommMapper.insertDeptExtHis(condition);
			SysDeptExtExample example = new SysDeptExtExample();
			example.createCriteria().andDeptIdEqualTo(record.getDeptId());
			sysDeptExtMapper.deleteByExample(example);
		}
		return count;
	}
	
	@Override
	public int updateDeptByO(SysDept record) throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("deptId", record.getDeptId());
		condition.put("bussin_type", "E");
		sysPowerCommMapper.insertDeptHis(condition);
		int count = this.updateDept(record);
		return count;
	}
	@Override
	public void updateDeptExts(Integer deptId, List<SysDeptExt> recordExts) throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("deptId", deptId);
		condition.put("bussin_type", "E");
		if (recordExts != null) {
			for (SysDeptExt cext : recordExts) {
				cext.setDeptId(deptId);
				
				if (this.countDeptExtByIdAndKey(deptId, cext.getExtKey()) == 0) {
					this.insertDeptExt(cext);
				} else {
					condition.put("key", cext.getExtKey());
					sysPowerCommMapper.insertDeptExtHis(condition);
					this.updateDeptExt(cext);
				}
			}
		} else {
			sysPowerCommMapper.insertDeptExtHis(condition);
			SysDeptExtExample example = new SysDeptExtExample();
			example.createCriteria().andDeptIdEqualTo(deptId);
			sysDeptExtMapper.deleteByExample(example);
		}
	}

	@Override
	@CacheEvict(value="sysCache",key="'SYS_DEPT'")  
	public Integer createDept(SysDept record, List<SysDeptExt> recordExts) throws SystemException, BusinessException {
		record.setCreateTime(new Timestamp(System.currentTimeMillis()));
		record.setDeptStatus("1");
		Integer deptId = this.insertDept(record);
			try {
			if (recordExts != null) {

				for (SysDeptExt cext : recordExts) {
					cext.setDeptId(deptId);
					this.insertDeptExt(cext);	
				}
			}
		} catch (Exception e) {
			throw new SystemException(new DefaultExceptionCode("9999", e.getMessage()));
		}
		return deptId;
	}
	
	@Override
	public void updateDeptStatusByDeptIds(List<Integer> deptIds, String deptStatus, Integer updator) throws SystemException, BusinessException 
	{
		Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("bussin_type", "A");
		for(Integer deptId : deptIds) 
		{
			condition.put("deptId", deptId);
			sysPowerCommMapper.insertDeptHis(condition);
			this.updateDeptStatusById(deptId, deptStatus, updator);
		}
	}


}
