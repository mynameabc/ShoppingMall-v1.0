package model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_role_resources")
public class RoleResources implements java.io.Serializable {

    @Id
    @Column(name = "ID")
    private String ID;

    @Column(name = "roleID")
    private Integer roleID;

    @Column(name = "resourcesID")
    private Integer resourcesID;

    public RoleResources() {}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getResourcesID() {
        return resourcesID;
    }

    public void setResourcesID(Integer resourcesID) {
        this.resourcesID = resourcesID;
    }
}
