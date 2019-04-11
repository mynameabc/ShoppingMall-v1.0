package auxiliary.loginObject;

import model.entity.Employee;

public class GuaranteeAuthorization {

    private Long employeeID;
    private Employee employee;
    private String menuList;
    private String bottonList;

    public GuaranteeAuthorization() {}

    public GuaranteeAuthorization(Employee employee) {
        this.employeeID = employee.getEmployeeID();
        this.employee = employee;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getMenuList() {
        return menuList;
    }

    public void setMenuList(String menuList) {
        this.menuList = menuList;
    }

    public String getBottonList() {
        return bottonList;
    }

    public void setBottonList(String bottonList) {
        this.bottonList = bottonList;
    }
}
