package pers.spring4.day03.n1_ioc_annotaion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.spring4.day03.n1_ioc_annotaion.dao.BaseDao;


public class BaseService<T> {
	@Autowired
	private BaseDao<T> baseDao;
	
	public void save(){
		System.out.println("自动注入的dao："+baseDao);
		baseDao.save();
	}
}
