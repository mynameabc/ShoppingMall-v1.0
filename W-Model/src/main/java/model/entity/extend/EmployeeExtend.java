package model.entity.extend;

import model.entity.Employee;

public class EmployeeExtend extends Employee {

    public EmployeeExtend() {
        super();
    }

    private String officeName;      //机构名称

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}

