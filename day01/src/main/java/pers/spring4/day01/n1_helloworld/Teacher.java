package pers.spring4.day01.n1_helloworld;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-14:05
 */
public class Teacher {
    private String teaName;

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teaName='" + teaName + '\'' +
                '}';
    }
}
