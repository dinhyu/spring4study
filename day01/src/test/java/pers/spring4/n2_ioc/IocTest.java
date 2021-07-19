package pers.spring4.n2_ioc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.spring4.day01.n2_ioc.bean.Emp;
import pers.spring4.day01.n2_ioc.bean.Shop;
import pers.spring4.day01.n2_ioc.service.UserService;


/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-14:35
 */
public class IocTest {
    ApplicationContext applicationContext =new ClassPathXmlApplicationContext("springconfig_n2.xml");
    @Test
    public void testService(){
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.update();
        Assert.assertNotNull(userService);
    }
    @Test
    public void testEmp(){
        Emp emp = applicationContext.getBean("emp", Emp.class);
        System.out.println(emp);
        Assert.assertNotNull(emp);
    }
    @Test
    public void testShop(){
        Shop shop = applicationContext.getBean("shop", Shop.class);
        System.out.println(shop);
        Assert.assertNotNull(shop);
    }
}
