package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.TABLE25;
import com.bdx.rainbow.entity.etl.TABLE25Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TABLE25Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int countByExample(TABLE25Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByExample(TABLE25Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insert(TABLE25 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insertSelective(TABLE25 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    List<TABLE25> selectByExample(TABLE25Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    TABLE25 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") TABLE25 record, @Param("example") TABLE25Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExample(@Param("record") TABLE25 record, @Param("example") TABLE25Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKeySelective(TABLE25 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKey(TABLE25 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table25
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    void insertBatch(List<TABLE25> recordLst);
}