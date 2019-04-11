package model.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_employee")
public class Employee implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeID")
    private Long employeeID;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "jobNumber")
    private String jobNumber;

    @Column(name = "pws")
    private String pws;

    @Column(name = "realName")
    private String realName;

    @Column(name = "officeID")
    private Integer officeID;

    @Column(name = "photo")
    private String photo;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "loginLock")
    private Boolean loginLock;

    @Column(name = "delFlag")
    private Boolean delFlag;

    @Column(name = "createTime")
    private Date createTime;

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

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

    public String getPws() {
        return pws;
    }

    public void setPws(String pws) {
        this.pws = pws;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getOfficeID() {
        return officeID;
    }

    public void setOfficeID(Integer officeID) {
        this.officeID = officeID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getLoginLock() {
        return loginLock;
    }

    public void setLoginLock(Boolean loginLock) {
        this.loginLock = loginLock;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
