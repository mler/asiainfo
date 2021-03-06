package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.TABLE33;
import com.bdx.rainbow.entity.etl.TABLE33Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TABLE33Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int countByExample(TABLE33Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByExample(TABLE33Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insert(TABLE33 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insertSelective(TABLE33 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    List<TABLE33> selectByExample(TABLE33Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    TABLE33 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") TABLE33 record, @Param("example") TABLE33Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExample(@Param("record") TABLE33 record, @Param("example") TABLE33Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKeySelective(TABLE33 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKey(TABLE33 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table33
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    void insertBatch(List<TABLE33> recordLst);
}