package pers.spring4.day01.n2_ioc.service;


import pers.spring4.day01.n2_ioc.dao.UserDao;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-14:32
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void update() {
        System.out.println("UserServiceImpl update");
        userDao.update();
    }
}
