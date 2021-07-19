package pers.spring4.day02.n2_ioc.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.spring4.day02.n2_ioc.bean.Manager;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 8:24
 */
public class IOCTest {
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n2.xml");
    @Test
    public void testBeanInitDestroyMethod(){
        Manager manager = applicationContext.getBean("manager", Manager.class);
        System.out.println(manager);
        ((ClassPathXmlApplicationContext)applicationContext).close();
        Assert.assertNotNull(manager);
    }
}
