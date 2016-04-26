package com.bdx.rainbow.urs.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DeptModel implements Serializable{

    private Integer deptId;

    private String deptName;

    private Integer deptPid;

    private String deptType;

    private String deptStatus;
    private String province;

    private String areaid;

    private String countyid;
    private String admin;
    private Map<String,String> deptExts;

    public Map<String, String> getDeptExts() {
        return deptExts;
    }

    public void setDeptExts(Map<String, String> deptExts) {
        this.deptExts = deptExts;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptPid() {
        return deptPid;
    }

    public void setDeptPid(Integer deptPid) {
        this.deptPid = deptPid;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getCountyid() {
        return countyid;
    }

    public void setCountyid(String countyid) {
        this.countyid = countyid;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }



}
