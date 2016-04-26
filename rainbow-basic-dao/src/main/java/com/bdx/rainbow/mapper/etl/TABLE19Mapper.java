package com.bdx.rainbow.mapper.etl;

import com.bdx.rainbow.entity.etl.TABLE19;
import com.bdx.rainbow.entity.etl.TABLE19Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TABLE19Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int countByExample(TABLE19Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByExample(TABLE19Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insert(TABLE19 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int insertSelective(TABLE19 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    List<TABLE19> selectByExample(TABLE19Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    TABLE19 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") TABLE19 record, @Param("example") TABLE19Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByExample(@Param("record") TABLE19 record, @Param("example") TABLE19Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKeySelective(TABLE19 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    int updateByPrimaryKey(TABLE19 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table19
     *
     * @mbggenerated Fri Aug 21 15:51:17 CST 2015
     */
    void insertBatch(List<TABLE19> recordLst);
}