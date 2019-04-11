package model.entity.extend;

import model.entity.Recharge;

import javax.persistence.Column;

public class RechargePagination extends Recharge implements java.io.Serializable {

    @Column(name = "mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
