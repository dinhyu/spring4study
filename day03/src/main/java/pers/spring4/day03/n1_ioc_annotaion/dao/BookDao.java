package pers.spring4.day03.n1_ioc_annotaion.dao;


import org.springframework.stereotype.Repository;
import pers.spring4.day03.n1_ioc_annotaion.bean.Book;

@Repository
public class BookDao extends BaseDao<Book>{
	@Override
	public void save() {
		// TODO Auto-generated method stub
		System.out.println("BookDao....保存图书。。。");
	}
}
