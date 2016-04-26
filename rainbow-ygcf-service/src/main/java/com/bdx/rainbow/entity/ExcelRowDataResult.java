package com.bdx.rainbow.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel数据读取结果
 * Created by fusj on 16/1/20.
 */
public class ExcelRowDataResult {
    /**
     * 读取结果
     */
    private Boolean flag;

    /**
     * 读取异常时异常结果描述
     */
    private String msg;

    /**
     * Excel一行数据
     */
    private Map<String, String> data;

    public ExcelRowDataResult() {
        this.flag = true;
        this.msg = "";
        this.data = new HashMap<String, String>();
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
