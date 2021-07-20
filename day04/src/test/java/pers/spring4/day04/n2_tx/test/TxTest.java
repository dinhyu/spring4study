package pers.spring4.day04.n2_tx.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.spring4.day04.n2_tx.service.BookService;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-20 12:45
 */
public class TxTest {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("springconfig_n2.xml");
    @Test
    public void test() {
        BookService bookService = ioc.getBean(BookService.class);

        bookService.checkout("Tom", "ISBN-001");

        System.out.println("结账完成");
    }
}
