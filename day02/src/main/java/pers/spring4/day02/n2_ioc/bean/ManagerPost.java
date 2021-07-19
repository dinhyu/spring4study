package pers.spring4.day02.n2_ioc.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-19 8:37
 */
public class ManagerPost implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化之前执行的后置处理器方法");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化之后执行的后置处理器方法");
        return bean;
    }
}
