package pers.spring4.day04.n1_jdbcTemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.spring4.day04.n1_jdbcTemplate.bean.User;
import pers.spring4.day04.n1_jdbcTemplate.dao.UserDao;

import java.util.List;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-20 10:51
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    //添加
    public void addUser(User user){
        userDao.addUser(user);
    }
    //修改
    public void updateUser(User user){
        userDao.updateUser(user);
    }
    //删除
    public void deleteUser(String id){
        userDao.deleteUser(id);
    }
    //查询返回某个值
    public int getUserQuantity(){
        int count = userDao.getUserQuantity();
        return count;
    }
    //查询返回某个对象
    public User getUserById(String id){
        User user = userDao.getUserById(id);
        return user;
    }
    //查询返回集合
    public List<User> getAllUsers(){
        List<User> list = userDao.getAllUsers();
        return list;
    }

    //批量添加
    public void batchAddUser(List<Object[]> batchArgs){
        userDao.batchAddUser(batchArgs);
    }

    //批量修改
    public void batchUpdateUser(List<Object[]> batchArgs){
        userDao.batchUpdateUser(batchArgs);
    }

    //批量删除
    public void batchDeleteUser(List<Object[]> batchArgs){
        userDao.batchDeleteUser(batchArgs);
    }
}
