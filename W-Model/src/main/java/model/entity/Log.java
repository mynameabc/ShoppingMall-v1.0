package model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sys_operatelog")
public class Log implements java.io.Serializable {

    private Integer id;//操作日志ID

    private String username;//操作人

    private String module;//操作模块

    private String methods;//执行方法

    private String content;//操作内容

    private String actionurl;//请求路径

    private String ip;//IP

    private Date date;//操作时间

    private Byte commite;//执行描述（1:执行成功、2:执行失败）

    public Log() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActionurl() {
        return actionurl;
    }

    public void setActionurl(String actionurl) {
        this.actionurl = actionurl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getCommite() {
        return commite;
    }

    public void setCommite(Byte commite) {
        this.commite = commite;
    }
}
