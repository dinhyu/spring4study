package pers.spring4.day01.n2_ioc.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-15:39
 */
public class Dept {
    private String dname;
    public void setDname(String dname){
        this.dname = dname;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "dname='" + dname + '\'' +
                '}';
    }
}