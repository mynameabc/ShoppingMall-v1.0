package model.entity;

import javax.persistence.*;

@Table(name = "member_grade")
public class MemberGrade implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gradeID")
    private Integer gradeID;

    @Column(name = "name")
    private String name;

    @Column(name = "delFlag")
    private Boolean delFlag;

    @Column(name = "imgSrc")
    private String imgSrc;

    @Column(name = "rebate")
    private String rebate;

    public Integer getGradeID() {
        return gradeID;
    }

    public void setGradeID(Integer gradeID) {
        this.gradeID = gradeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }
}