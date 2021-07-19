package pers.spring4.day02.n2_ioc.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 11:09
 */
public class Department {
    private String depName;

    public void setDepName(String depName) {
        this.depName = depName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "depName='" + depName + '\'' +
                '}';
    }
}
