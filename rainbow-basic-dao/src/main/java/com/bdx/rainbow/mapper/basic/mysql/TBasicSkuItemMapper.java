package com.bdx.rainbow.mapper.basic.mysql;

import com.bdx.rainbow.entity.basic.mysql.TBasicSkuItem;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBasicSkuItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int countByExample(TBasicSkuItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int deleteByExample(TBasicSkuItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int deleteByPrimaryKey(Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int insert(TBasicSkuItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int insertSelective(TBasicSkuItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    List<TBasicSkuItem> selectByExample(TBasicSkuItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    TBasicSkuItem selectByPrimaryKey(Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int updateByExampleSelective(@Param("record") TBasicSkuItem record, @Param("example") TBasicSkuItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int updateByExample(@Param("record") TBasicSkuItem record, @Param("example") TBasicSkuItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int updateByPrimaryKeySelective(TBasicSkuItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    int updateByPrimaryKey(TBasicSkuItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_basic_sku_item
     *
     * @mbggenerated Mon Jan 25 17:41:18 CST 2016
     */
    void insertBatch(List<TBasicSkuItem> recordLst);
}