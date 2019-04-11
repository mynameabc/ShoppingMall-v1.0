package model.dto;

import communal.Result;
import communal.util.RegexUtil;

import java.util.Date;

public class LoginDTO implements java.io.Serializable {

    private String account;         //登陆账号
    private String password;        //登陆密码

    private Date loginTime;         //登陆时间
    private String ipAddress;       //登陆IP
    private String loginAddress;    //登陆所在地
    private String equipment;       //使用的设备
    private String model;           //设备型号

    public LoginDTO() {}

    public LoginDTO(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public Result checkDTO() {

        if (!RegexUtil.mobile(this.account)) {
            return new Result(false, "请输入正确的手机号码!");
        }

        if (!RegexUtil.password(this.account)) {
            return new Result(false, "大小写字母和数字的组合，不能使用特殊字符，长度在6-10之间!");
        }

        return new Result(true, "");
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
