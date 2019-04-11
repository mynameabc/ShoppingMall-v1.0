package model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_role_employee")
public class RoleEmployee implements java.io.Serializable {

    @Id
    @Column(name = "ID")
    private String ID;

    @Column(name = "roleID")
    private Integer roleID;

    @Column(name = "employeeID")
    private Integer employeeID;

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

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }
}
