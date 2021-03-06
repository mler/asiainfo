package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.TABLE21;
import com.bdx.rainbow.entity.etl.TABLE21Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TABLE21Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int countByExample(TABLE21Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByExample(TABLE21Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insert(TABLE21 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insertSelective(TABLE21 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    List<TABLE21> selectByExample(TABLE21Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    TABLE21 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") TABLE21 record, @Param("example") TABLE21Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExample(@Param("record") TABLE21 record, @Param("example") TABLE21Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKeySelective(TABLE21 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKey(TABLE21 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table21
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    void insertBatch(List<TABLE21> recordLst);
}