package com.bdx.rainbow.entity.basic.mysql;

public class TBasicSkuImagKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_basic_sku_imag.SKU_ID
     *
     * @mbggenerated Wed Jan 27 11:51:34 CST 2016
     */
    private Integer skuId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_basic_sku_imag.VFS_ID
     *
     * @mbggenerated Wed Jan 27 11:51:34 CST 2016
     */
    private String vfsId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_basic_sku_imag.SKU_ID
     *
     * @return the value of t_basic_sku_imag.SKU_ID
     *
     * @mbggenerated Wed Jan 27 11:51:34 CST 2016
     */
    public Integer getSkuId() {
        return skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_basic_sku_imag.SKU_ID
     *
     * @param skuId the value for t_basic_sku_imag.SKU_ID
     *
     * @mbggenerated Wed Jan 27 11:51:34 CST 2016
     */
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_basic_sku_imag.VFS_ID
     *
     * @return the value of t_basic_sku_imag.VFS_ID
     *
     * @mbggenerated Wed Jan 27 11:51:34 CST 2016
     */
    public String getVfsId() {
        return vfsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_basic_sku_imag.VFS_ID
     *
     * @param vfsId the value for t_basic_sku_imag.VFS_ID
     *
     * @mbggenerated Wed Jan 27 11:51:34 CST 2016
     */
    public void setVfsId(String vfsId) {
        this.vfsId = vfsId == null ? null : vfsId.trim();
    }
}