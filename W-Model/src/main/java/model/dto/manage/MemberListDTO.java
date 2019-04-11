package model.dto.manage;

public class MemberListDTO implements java.io.Serializable {

    private String name;
    private String email;
    private String mobile;
    private Integer memberGradeID;
    private long parentID;
    private int login_lock;

    public MemberListDTO() {}

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

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public int getLogin_lock() {
        return login_lock;
    }

    public void setLogin_lock(int login_lock) {
        this.login_lock = login_lock;
    }

    public Integer getMemberGradeID() {
        return memberGradeID;
    }

    public void setMemberGradeID(Integer memberGradeID) {
        this.memberGradeID = memberGradeID;
    }
}
