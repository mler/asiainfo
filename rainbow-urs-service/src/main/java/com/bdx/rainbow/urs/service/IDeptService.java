package com.bdx.rainbow.urs.service;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.entity.urs.*;

import java.util.List;
import java.util.Map;

public interface IDeptService {
	Integer insertDept(SysDept record) throws SystemException, BusinessException;
	int updateDept(SysDept record) throws SystemException, BusinessException;
	/**
	 * obj为null时，则条件为空
	 * condition: dept_name(like), dept_type, dept_pid, dept_status, province, areaid, countyid
	 * @param record
	 * @param start
	 * @param count
	 * @return
	 */
	List<SysDept> getDepts(SysDept record, Integer start, Integer count) throws SystemException, BusinessException;
	
	/**
	 * use primaryKEY find Sys_Dept obj & sys_dept_ext obj
	 * @param deptId
	 * @return
	 */
	SysDeptModel getDeptAndExtById(Integer deptId) throws SystemException, BusinessException;
	
	/**
	 * use primaryKEY find Sys_Dept
	 * @param deptId
	 * @return
	 */
	SysDept getDeptById(Integer deptId) throws SystemException, BusinessException;

	/**
	 * use primaryKEY find  sys_dept_ext objs
	 * @param deptId
	 * @return
	 */
	List<SysDeptExt> getDeptExtById(Integer deptId) throws SystemException, BusinessException;
	
	/**
	 * use ID and key find sys_dept_ext obj
	 * @param deptId
	 * @param key
	 * @return
	 */
	SysDeptExt getDeptExtByIdAndKey(Integer deptId, String key) throws SystemException, BusinessException;
	
	/**
	 * update Sys_Dept's dept_status
	 * @param deptId
	 * @param deptStatus : 1 正常， 2禁用 ，3 无效
	 * @return
	 */
	 
	int updateDeptStatusById(Integer deptId, String deptStatus, Integer updator) throws SystemException, BusinessException;
	
	int countDept(SysDept record) throws SystemException, BusinessException;
	
	int countDeptExtByIdAndKey(Integer deptId, String extKey) throws SystemException, BusinessException;
	
	/**
	 * create Sys_Dept、sys_dept_ext message
	 * @param record
	 * @param recordExts
	 * @return
	 */
	public abstract int updateDept(SysDept record, List<SysDeptExt> recordExts) throws SystemException, BusinessException;
	/**
	 * update Sys_Dept、sys_dept_ext message,
	 * if sys_dept_ext's key was existed ,then to update sys_dept_ext obj.otherwise to insert sys_dept_ext
	 * @param record
	 * @param recordExts
	 * @return
	 */
	public abstract Integer createDept(SysDept record, List<SysDeptExt> recordExts) throws SystemException, BusinessException;
	
	/**
	 * 返回树形结构列表
	 * @param condition
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	List<SysDeptTree> getSysDeptTree(Map<String, Object> condition)
			throws SystemException, BusinessException;
	
	/**
	 * 批量变更组织机构状态
	 * @param deptIds
	 * @param deptStatus
	 * @param updator
	 * @throws SystemException
	 * @throws BusinessException
	 */
	void updateDeptStatusByDeptIds(List<Integer> deptIds, String deptStatus,
			Integer updator) throws SystemException, BusinessException;


	DefaultTree<SysDeptTree> getDeptTree(Map<String, Object> condition)
			throws SystemException, BusinessException;
	
	/**
	 * 新增扩展信息
	 * @param deptExt
	 * @throws SystemException
	 * @throws BusinessException
	 */
	void insertDeptExt(SysDeptExt deptExt) throws SystemException, BusinessException;
	
	/**
	 * 更新扩展信息
	 * @param deptExt
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	int updateDeptExt(SysDeptExt deptExt) throws SystemException, BusinessException;
	
	/**
	 * 更新组织机构扩展信息，如果recordExts为null，则删除sys_dept_ext中对应的deptId的记录。否则规则如下：判断是否已经存在对应KEY的数据，如无，则新增，反之更新；
	 * @param deptId
	 * @param recordExts
	 * @throws SystemException
	 * @throws BusinessException
	 */
	void updateDeptExts(Integer deptId, List<SysDeptExt> recordExts)
			throws SystemException, BusinessException;
	/**
	 * 更新组织机构信息，不包含扩展信息
	 * @param record
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	int updateDeptByO(SysDept record) throws SystemException, BusinessException;
    public void insertDeptHis(SysDeptHis record)throws SystemException, BusinessException;
}
