package pers.spring4.day02.n3_spel.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 13:28
 */
public class Department {
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptName='" + deptName + '\'' +
                '}';
    }
}