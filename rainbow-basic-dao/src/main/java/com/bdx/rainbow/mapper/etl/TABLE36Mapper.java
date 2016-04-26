package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.TABLE36;
import com.bdx.rainbow.entity.etl.TABLE36Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TABLE36Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int countByExample(TABLE36Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByExample(TABLE36Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insert(TABLE36 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insertSelective(TABLE36 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    List<TABLE36> selectByExample(TABLE36Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    TABLE36 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") TABLE36 record, @Param("example") TABLE36Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExample(@Param("record") TABLE36 record, @Param("example") TABLE36Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKeySelective(TABLE36 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKey(TABLE36 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table36
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    void insertBatch(List<TABLE36> recordLst);
}