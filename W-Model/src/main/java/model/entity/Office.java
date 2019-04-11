package model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "sys_office")
public class Office implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "officeID", insertable = false)
    private Integer officeID;

    @Column(name = "parentID")
    private Integer parentID;

    @Column(name = "areaID")
    private String areaID;

    @Column(name = "treeGrade")
    private Integer treeGrade;

    @Column(name = "name")
    private String name;

    @Column(name = "sort")
    private BigDecimal sort;

    @Column(name = "type")
    private Integer type;

    @Column(name = "address")
    private String address;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "master")
    private String master;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "useable")
    private Boolean useable;

    @Column(name = "remarks")
    private String remarks;

    public Integer getOfficeID() {
        return officeID;
    }

    public void setOfficeID(Integer officeID) {
        this.officeID = officeID;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public Integer getTreeGrade() {
        return treeGrade;
    }

    public void setTreeGrade(Integer treeGrade) {
        this.treeGrade = treeGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}