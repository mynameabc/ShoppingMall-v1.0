package model.dto;

import java.util.Date;

public class RechargeDTO implements java.io.Serializable {

    private Date startDateTime;
    private Date endDateTime;
    private Long memberID;
    private String mobile;
    private Integer mode;
    private String platformSerialNumber;

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
