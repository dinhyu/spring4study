package pers.spring4.day02.n1_ioc.factory;

import org.springframework.beans.factory.FactoryBean;
import pers.spring4.day02.n1_ioc.bean.Employee;

/**
 * @author dinhyu
 * @version v1.0
 * date 2021-07-18-16:59
 */
public class EmployeeFactoryBeanImpl implements FactoryBean<Employee>{
    @Override
    public Employee getObject() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeName("vivy");
        employee.setEmployeeAge(100);
        return employee;
    }

    @Override
    public Class<?> getObjectType() {
        return Employee.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
