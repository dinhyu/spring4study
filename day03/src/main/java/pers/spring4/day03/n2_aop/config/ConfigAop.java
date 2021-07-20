package pers.spring4.day03.n2_aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//配置类
@Configuration//标识为配置类
@ComponentScan(basePackages = {"pers.spring4.day03.n2_aop"})//开启包扫描
@EnableAspectJAutoProxy(proxyTargetClass = true)//开启基于注解的AOP功能,允许代理对象使用被代理接口的子类承接
public class ConfigAop {}
