package pers.spring4.day02.n3_spel.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 13:27
 */
public class Employee {
    private String empId;
    private String empName;
    private Integer age;
    private Department dept;
    private String deptName;
    private Double salaryOfYear;
    private Double circle;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Double getSalaryOfYear() {
        return salaryOfYear;
    }

    public void setSalaryOfYear(Double salaryOfYear) {
        this.salaryOfYear = salaryOfYear;
    }

    public Double getCircle() {
        return circle;
    }

    public void setCircle(Double circle) {
        this.circle = circle;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", age=" + age +
                ", dept=" + dept +
                ", deptName='" + deptName + '\'' +
                ", salaryOfYear=" + salaryOfYear +
                ", circle=" + circle +
                '}';
    }
}
