package pers.spring4.day02.n3_spel.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.spring4.day02.n3_spel.bean.Employee;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 13:32
 */
public class SpelTest {
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n3.xml");
    @Test
    public void testRefBean(){
        Employee emp1 = applicationContext.getBean("emp1", Employee.class);
        System.out.println(emp1);
        Assert.assertNotNull(emp1);
    }
}
