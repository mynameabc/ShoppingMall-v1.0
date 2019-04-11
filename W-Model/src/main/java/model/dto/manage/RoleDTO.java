package model.dto.manage;

public class RoleDTO implements java.io.Serializable {

    private Integer roleID;

    private Integer validFlag = null;  //有效性标识(0:无效, 1:有效)

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }
}
