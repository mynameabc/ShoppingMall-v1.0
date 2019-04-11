package model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "sys_resources")
public class Resources implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resourcesID")
    private Integer resourcesID;

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

    @Column(name = "type")
    private Integer type;

    public Integer getResourcesID() {
        return resourcesID;
    }

    public void setResourcesID(Integer resourcesID) {
        this.resourcesID = resourcesID;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}