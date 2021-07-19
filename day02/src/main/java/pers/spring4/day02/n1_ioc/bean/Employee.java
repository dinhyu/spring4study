package pers.spring4.day02.n1_ioc.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-16:37
 */
public class Employee {
    private String employeeName;
    private Integer employeeAge;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(Integer employeeAge) {
        this.employeeAge = employeeAge;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeName='" + employeeName + '\'' +
                ", employeeAge=" + employeeAge +
                '}';
    }
}
