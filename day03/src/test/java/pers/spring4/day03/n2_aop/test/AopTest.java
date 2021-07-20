package pers.spring4.day03.n2_aop.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pers.spring4.day03.n2_aop.bean.CalculatorImpl;
import pers.spring4.day03.n2_aop.config.ConfigAop;
import pers.spring4.day03.n2_aop.inter.Calculator;
import pers.spring4.day03.n2_aop.proxy.CalculatorProxy;

public class AopTest {
    
    @Test
    public void testProxy(){
        Calculator proxy = CalculatorProxy.getProxy(new CalculatorImpl());
        Double sum = proxy.getSum(1.0, 2.0);
        System.out.println(sum);
    }

    @Test
    public void testAop(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n2.xml");
        Calculator calculator = applicationContext.getBean(Calculator.class);
        Double sum = calculator.getSum(1.0,2.0);
        System.out.println("testAop:" + sum);
    }
    @Test
    public void testAopAnnoWithoutXml(){
        //完全注解开发使用AnnotationConfigApplicationContext读取配置类的class类对象
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigAop.class);
        Calculator calculator = applicationContext.getBean(Calculator.class);
        Double sum = calculator.getSum(1.0,2.0);
        System.out.println("testAop:" + sum);
    }
}
