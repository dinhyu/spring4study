package pers.spring4.day02.n2_ioc.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 8:27
 */
public class Manager {
    private String mname;

    public Manager() {
        System.out.println("1.无参构造执行");
    }

    public void setMname(String mname) {
        this.mname = mname;
        System.out.println("2.set方法执行");
    }

    //创建执行的初始化方法
    public void initMethod(){
        System.out.println("3.初始化方法执行");
    }

    //创建执行的销毁的方法
    public void destroyMethod(){
        System.out.println("5.销毁方法执行");
    }
}
