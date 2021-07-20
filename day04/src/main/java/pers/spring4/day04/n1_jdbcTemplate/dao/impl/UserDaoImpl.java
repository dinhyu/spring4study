package pers.spring4.day04.n1_jdbcTemplate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pers.spring4.day04.n1_jdbcTemplate.bean.User;
import pers.spring4.day04.n1_jdbcTemplate.dao.UserDao;

import java.util.Arrays;
import java.util.List;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-20 10:51
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(User user) {
        //创建sql语句
        String sql = "insert into t_user values(null,?,?)";
        //调用方法实现
        int update = jdbcTemplate.update(sql,user.getUsername(),user.getUstatus());
        System.out.println(update);
    }

    @Override
    public void updateUser(User user) {
        //创建sql语句
        String sql = "update t_user set username=?,ustatus=? where user_id=?";
        //调用方法实现
        int update = jdbcTemplate.update(sql,user.getUsername(),user.getUstatus(),user.getUserId());
        System.out.println(update);
    }

    @Override
    public void deleteUser(String id) {
        //创建sql语句
        String sql = "delete from t_user where user_id=?";
        //调用方法实现
        int update = jdbcTemplate.update(sql,id);
        System.out.println(update);
    }

    @Override
    public int getUserQuantity() {
        //创建sql语句
        String sql = "select count(*) from t_user";
        //调用方法实现
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count;
    }

    @Override
    public User getUserById(String id) {
        //创建sql语句
        String sql = "select * from t_user where user_id=?";
        //调用方法实现
        User user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),id);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        //创建sql语句
        String sql = "select * from t_user";
        //调用方法实现
        List<User> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
        return list;
    }

    @Override
    public void batchAddUser(List<Object[]> batchArgs) {
        //创建sql语句
        String sql = "insert into t_user values(null,?,?)";
        //调用方法实现
        int[] ints = jdbcTemplate.batchUpdate(sql,batchArgs);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdateUser(List<Object[]> batchArgs) {
        //创建sql语句
        String sql = "update t_user set username=?,ustatus=? where user_id=?";
        //调用方法实现
        int[] ints = jdbcTemplate.batchUpdate(sql,batchArgs);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchDeleteUser(List<Object[]> batchArgs) {
        //创建sql语句
        String sql = "delete from t_user where user_id=?";
        //调用方法实现
        int[] ints = jdbcTemplate.batchUpdate(sql,batchArgs);
        System.out.println(Arrays.toString(ints));
    }
}
