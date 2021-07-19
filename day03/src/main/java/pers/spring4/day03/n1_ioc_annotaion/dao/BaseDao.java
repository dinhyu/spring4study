package pers.spring4.day03.n1_ioc_annotaion.dao;

/**
 * 定义了基本的增删改查方法
 * @author lfy
 *
 * @param <T>
 */
public abstract class BaseDao<T> {
	
	public abstract void save();

}
