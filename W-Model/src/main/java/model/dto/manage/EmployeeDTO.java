package model.dto.manage;

public class EmployeeDTO implements java.io.Serializable {

    private String name = null;        //登陆名
    private String email = null;       //邮箱
    private String mobile = null;      //手机号码
    private String jobNumber = null;   //工号
    private String realName = null;    //真实姓名
    private String officeID = null;   //机构ID
    private Integer loginLock = null;  //登陆锁
    private Integer delFlag = null;    //删除标识

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOfficeID() {
        return officeID;
    }

    public void setOfficeID(String officeID) {
        this.officeID = officeID;
    }

    public Integer getLoginLock() {
        return loginLock;
    }

    public void setLoginLock(Integer loginLock) {
        this.loginLock = loginLock;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
