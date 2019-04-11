package model.entity.extend;

import model.entity.Member;

import java.math.BigDecimal;

public class MemberExtend extends Member {

    public MemberExtend() {
        super();
    }

    private String useMoney;    //可用余额
    private String storeCount;     //门店数量

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(String storeCount) {
        this.storeCount = storeCount;
    }
}
