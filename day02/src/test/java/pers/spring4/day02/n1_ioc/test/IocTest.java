package pers.spring4.day02.n1_ioc.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.spring4.day02.n1_ioc.bean.Employee;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-16:44
 */
public class IocTest {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n1.xml");
    @Test
    public void testFactory(){
        Employee employee = applicationContext.getBean("employee", Employee.class);
        System.out.println(employee);

        Employee employee2 = applicationContext.getBean("employee2", Employee.class);
        System.out.println(employee2);

        Employee employee3 = applicationContext.getBean("employeeFactoryBeanImpl", Employee.class);
        System.out.println(employee3);
    }

}
