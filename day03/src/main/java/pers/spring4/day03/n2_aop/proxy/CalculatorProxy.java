package pers.spring4.day03.n2_aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CalculatorProxy {
    public static<T> T getProxy(final T obj){
        //方法执行器帮我们的目标对象执行目标方法
        InvocationHandler ih = new InvocationHandler(){
            /**
             * @param proxy 代理对象
             * @param method 当前将要执行的目标对象的方法
             * @param args 目标方法调用时传入的参数
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // TODO Auto-generated method stub

                Object result = null;
                
                try {
                    System.out.println("前置通知");
                    //利用反射执行目标方法
                    //目标方法执行后的返回值
                    result = method.invoke(obj, args);
                    System.out.println("返回通知");
                } catch (Exception e) {
                    //TODO: handle exception
                    e.printStackTrace();
                    System.out.println("异常通知");
                }finally{
                    System.out.println("后置通知");
                }
                
                return result;
            }
            
        };
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        ClassLoader classLoader = obj.getClass().getClassLoader();

        //创建代理对象
        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, ih);
        return (T)proxy;
    }
}
