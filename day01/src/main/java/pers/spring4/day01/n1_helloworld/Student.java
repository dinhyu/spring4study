package pers.spring4.day01.n1_helloworld;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-10:23
 */
public class Student {
    private Integer studentId;
    private String stuName;
    private int age;
    private Teacher teacher;

    public Student() {
    }

    public Student(Integer studentId, String stuName, int age) {
        this.studentId = studentId;
        this.stuName = stuName;
        this.age = age;
    }

    public Student(Integer studentId, int age, String stuName) {
        this.studentId = studentId;
        this.stuName = stuName;
        this.age = age;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", stuName='" + stuName + '\'' +
                ", age=" + age +
                ", teacher=" + teacher +
                '}';
    }
}
