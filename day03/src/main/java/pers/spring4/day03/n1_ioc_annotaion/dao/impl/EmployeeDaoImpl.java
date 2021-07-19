package pers.spring4.day03.n1_ioc_annotaion.dao.impl;

import org.springframework.stereotype.Repository;

import pers.spring4.day03.n1_ioc_annotaion.dao.EmployeeDao;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{

    @Override
    public void update() {
        System.out.println("EmployeeDaoImpl update");
    }
    
}
