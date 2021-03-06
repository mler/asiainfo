package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.TABLE5;
import com.bdx.rainbow.entity.etl.TABLE5Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TABLE5Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int countByExample(TABLE5Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByExample(TABLE5Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insert(TABLE5 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insertSelective(TABLE5 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    List<TABLE5> selectByExample(TABLE5Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    TABLE5 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") TABLE5 record, @Param("example") TABLE5Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExample(@Param("record") TABLE5 record, @Param("example") TABLE5Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKeySelective(TABLE5 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKey(TABLE5 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table5
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    void insertBatch(List<TABLE5> recordLst);
}