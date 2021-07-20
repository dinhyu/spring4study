package pers.spring4.day03.n3_aopxml.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pers.spring4.day03.n3_aopxml.bean.Book;

public class AopXmlTest {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n3.xml");
    @Test
    public void testAopXml(){
        Book book = applicationContext.getBean("book",Book.class);
        book.show();
    }
}
