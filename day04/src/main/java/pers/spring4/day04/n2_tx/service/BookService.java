package pers.spring4.day04.n2_tx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.spring4.day04.n2_tx.dao.BookDao;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-20 12:34
 */
@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    /**
     * 结账；传入哪个用户买了哪本书
     * @param username
     * @param isbn
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void checkout(String username,String isbn){
        //1、减库存
        bookDao.updateStock(isbn);

        int price = bookDao.getPrice(isbn);
        //2、减余额
        bookDao.updateBalance(username, price);
    }

    //修改价格
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void updatePrice(String isbn,int price){
        bookDao.updatePrice(isbn, price);
    }

    /**
     * 根据业务的特性；进行调整
     * isolation=Isolation.READ_UNCOMMITTED:读出脏数据
     *
     *
     * 		READ_COMMITTED；实际上业务逻辑中用的最多的也是这个；
     * 		REPEATABLEP_READ；
     * @param isbn
     * @return
     */
    @Transactional(readOnly=true)
    public int getPrice(String isbn){
        return bookDao.getPrice(isbn);
    }


    @Transactional
    public void mulTx(){

        //ioc.getBean("BookSerice");
        checkout("Tom", "ISBN-001");

        updatePrice("ISBN-002", 998);

        int i=10/0;
    }
}