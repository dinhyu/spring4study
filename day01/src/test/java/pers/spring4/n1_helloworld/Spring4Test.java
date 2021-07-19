package pers.spring4.n1_helloworld;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.spring4.day01.n1_helloworld.Student;

import static org.junit.Assert.assertNotNull;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-10:28
 */
public class Spring4Test {
    @Test
    public void testStudentBean(){
        //加载spring配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n1.xml");
        //获取配置创建的对象
        Student student = applicationContext.getBean("student", Student.class);
        System.out.println(student);
        assertNotNull(student);
    }
}
