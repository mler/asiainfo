package com.bdx.rainbow.mapper.ydzf;

import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TYdzfMonitorPlanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int countByExample(TYdzfMonitorPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int deleteByExample(TYdzfMonitorPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int deleteByPrimaryKey(Integer monitorPlanId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int insert(TYdzfMonitorPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int insertSelective(TYdzfMonitorPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    List<TYdzfMonitorPlan> selectByExample(TYdzfMonitorPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    TYdzfMonitorPlan selectByPrimaryKey(Integer monitorPlanId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int updateByExampleSelective(@Param("record") TYdzfMonitorPlan record, @Param("example") TYdzfMonitorPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int updateByExample(@Param("record") TYdzfMonitorPlan record, @Param("example") TYdzfMonitorPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int updateByPrimaryKeySelective(TYdzfMonitorPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    int updateByPrimaryKey(TYdzfMonitorPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ydzf_monitor_plan
     *
     * @mbggenerated Thu Apr 21 01:37:50 GMT+08:00 2016
     */
    void insertBatch(List<TYdzfMonitorPlan> recordLst);
}