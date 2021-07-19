package pers.spring4.day03.n1_ioc_annotaion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pers.spring4.day03.n1_ioc_annotaion.dao.EmployeeDao;
import pers.spring4.day03.n1_ioc_annotaion.service.EmployeeService;

//在注解里面 value 属性值可以省略不写，
//默认值是类名称，首字母小写
// @Service(value = "employeeName")
@Service(value = "employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    //添加注入属性注解
    @Autowired//根据属性类型进行自动装配
    @Qualifier(value = "employeeDaoImpl1")//有多个实现类时通过名称指定类型
    private EmployeeDao userDao;

    @Override
    public void add() {
        // TODO Auto-generated method stub
        System.out.println("EmployeeServiceImpl add");
        userDao.update();
    }
    
}
