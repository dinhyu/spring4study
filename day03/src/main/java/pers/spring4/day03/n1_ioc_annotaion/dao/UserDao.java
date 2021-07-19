package pers.spring4.day03.n1_ioc_annotaion.dao;

import org.springframework.stereotype.Repository;
import pers.spring4.day03.n1_ioc_annotaion.bean.User;


@Repository
public class UserDao extends BaseDao<User>{
	@Override
	public void save() {
		// TODO Auto-generated method stub
		System.out.println("UserDao...保存用户....");
	}
}