package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * Created by core on 15/10/14.
 *  台帐详细
 */
public class SK0005Request extends BDXBody {

    /**
     * 台帐类型
     */
    private String ledgerType;

    /**
     * 开始时间
     */
    private String begTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 分页条数
     */
    private String pageCount;
    /**
     * 当前页码
     */
    private String pageNumber;

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getLedgerType() {
        return ledgerType;
    }

    public void setLedgerType(String ledgerType) {
        this.ledgerType = ledgerType;
    }
}
