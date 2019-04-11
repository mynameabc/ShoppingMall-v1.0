package model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "product_category")
public class ProductCategory implements java.io.Serializable {

    @Id
    @Column(name = "categoryID")
    private Integer categoryID;

    private String name;

    @Column(name = "parentID")
    private Integer parentID;

    private BigDecimal sort;

    @Column(name = "showFlag")
    private Boolean showFlag;

    private String icon_1;

    private String icon_2;

    private String icon_3;

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public Boolean getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Boolean showFlag) {
        this.showFlag = showFlag;
    }

    public String getIcon_1() {
        return icon_1;
    }

    public void setIcon_1(String icon_1) {
        this.icon_1 = icon_1;
    }

    public String getIcon_2() {
        return icon_2;
    }

    public void setIcon_2(String icon_2) {
        this.icon_2 = icon_2;
    }

    public String getIcon_3() {
        return icon_3;
    }

    public void setIcon_3(String icon_3) {
        this.icon_3 = icon_3;
    }
}