package pers.spring4.day02.n1_ioc.factory;

import pers.spring4.day02.n1_ioc.bean.Employee;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-16:49
 */
public class EmployeeInstanceFactory {
    public Employee getEmployee(String name){
        System.out.println("EmployeeInstanceFactory creating Employee");
        Employee employee = new Employee();
        employee.setEmployeeName(name);
        employee.setEmployeeAge(24);
        return employee;
    }
}
