package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.TABLE112;
import com.bdx.rainbow.entity.etl.TABLE112Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TABLE112Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int countByExample(TABLE112Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByExample(TABLE112Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insert(TABLE112 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insertSelective(TABLE112 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    List<TABLE112> selectByExample(TABLE112Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    TABLE112 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") TABLE112 record, @Param("example") TABLE112Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExample(@Param("record") TABLE112 record, @Param("example") TABLE112Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKeySelective(TABLE112 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKey(TABLE112 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table112
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    void insertBatch(List<TABLE112> recordLst);
}