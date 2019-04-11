package model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "member_recharge")
public class Recharge implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rechargeID")
    private Long rechargeID;

    @Column(name = "memberID")
    private Long memberID;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "beforeMoney")
    private BigDecimal beforeMoney;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "afterMoney")
    private BigDecimal afterMoney;

    @Column(name = "mode")
    private Integer mode;

    @Column(name = "platformSerialNumber")
    private String platformSerialNumber;

    public Long getRechargeID() {
        return rechargeID;
    }

    public void setRechargeID(Long rechargeID) {
        this.rechargeID = rechargeID;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(BigDecimal beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney) {
        this.afterMoney = afterMoney;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getPlatformSerialNumber() {
        return platformSerialNumber;
    }

    public void setPlatformSerialNumber(String platformSerialNumber) {
        this.platformSerialNumber = platformSerialNumber;
    }
}
