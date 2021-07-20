package pers.spring4.day04.n1_jdbcTemplate.dao;

import pers.spring4.day04.n1_jdbcTemplate.bean.User;

import java.util.List;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-20 10:50
 */
public interface UserDao {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(String id);

    int getUserQuantity();

    User getUserById(String id);

    List<User> getAllUsers();

    void batchAddUser(List<Object[]> batchArgs);

    void batchUpdateUser(List<Object[]> batchArgs);

    void batchDeleteUser(List<Object[]> batchArgs);
}
