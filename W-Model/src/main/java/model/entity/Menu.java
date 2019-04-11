package model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "sys_menu")
public class Menu implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuID")
    private Integer menuID;

    @Column(name = "parentID")
    private Integer parentID;

    @Column(name = "icon")
    private String icon;

    @Column(name = "title")
    private String title;

    @Column(name = "href")
    private String href;

    @Column(name = "sort")
    private BigDecimal sort;

    @Column(name = "showFlag")
    private Boolean showFlag;

    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
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
}