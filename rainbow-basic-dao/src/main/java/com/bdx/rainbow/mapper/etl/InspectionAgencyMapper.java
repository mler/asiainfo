package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.InspectionAgency;
import com.bdx.rainbow.entity.etl.InspectionAgencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InspectionAgencyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    int countByExample(InspectionAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    int deleteByExample(InspectionAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    int insert(InspectionAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    int insertSelective(InspectionAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    List<InspectionAgency> selectByExample(InspectionAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    int updateByExampleSelective(@Param("record") InspectionAgency record, @Param("example") InspectionAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    int updateByExample(@Param("record") InspectionAgency record, @Param("example") InspectionAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inspection_agency
     *
     * @mbggenerated Wed Jul 29 21:20:40 CST 2015
     */
    void insertBatch(List<InspectionAgency> recordLst);
}