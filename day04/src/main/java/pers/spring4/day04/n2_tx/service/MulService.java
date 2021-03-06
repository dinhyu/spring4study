package pers.spring4.day04.n2_tx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-20 12:54
 */
@Service
public class MulService {

    @Autowired
    private BookService bookService;

    @Transactional
    public void mulTx(){
        //都是可以设置的；
        //传播行为来设置这个事务方法是不是和之前的大事务共享一个事务（使用同一条连接）；
        //REQUIRED
        bookService.checkout("Tom", "ISBN-001");

        //REQUIRED   REQUIRES_NEW
        bookService.updatePrice("ISBN-002", 998);

        //int i = 10/0;
    }

}
