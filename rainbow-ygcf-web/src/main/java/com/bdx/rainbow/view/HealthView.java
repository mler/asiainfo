package com.bdx.rainbow.view;

import java.sql.Timestamp;

/**
 * 健康证视图
 * Created by fusj on 16/3/11.
 */
public class HealthView {
    private String fullName;

    private String healthCode;

    private Timestamp validDate;

    private String healthPath;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHealthCode() {
        return healthCode;
    }

    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode;
    }

    public Timestamp getValidDate() {
        return validDate;
    }

    public void setValidDate(Timestamp validDate) {
        this.validDate = validDate;
    }

    public String getHealthPath() {
        return healthPath;
    }

    public void setHealthPath(String healthPath) {
        this.healthPath = healthPath;
    }
}
