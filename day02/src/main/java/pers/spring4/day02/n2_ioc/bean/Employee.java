package pers.spring4.day02.n2_ioc.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 11:09
 */
public class Employee {
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "department=" + department +
                '}';
    }
}
