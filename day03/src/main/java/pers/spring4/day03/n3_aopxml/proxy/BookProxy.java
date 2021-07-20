package pers.spring4.day03.n3_aopxml.proxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class BookProxy {
    public void before(JoinPoint point){
        System.out.println("前置通知: " + point);
    }

    public void afterReturning(JoinPoint point,Object result){
        System.out.println("返回通知: " + point + ",result: " + result);
    }

    public void afterThrowing(JoinPoint point,Exception exception){
        System.out.println("异常通知: " + point + ",exception: " + exception);
    }

    public void after(JoinPoint point){
        System.out.println("后置通知: " + point);
    }

    public Object around(ProceedingJoinPoint point) throws Throwable{
        Object result = null;
        Object[] args = point.getArgs();
        String methodName = point.getSignature().getName();
        try {
            System.out.println("环绕前置通知: " + methodName);
            result = point.proceed(args);
            System.out.println("环绕返回通知: " + methodName + ",result: " + result);
        } catch (Exception e) {
            System.out.println("环绕异常通知: " + methodName + ",exception: " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            System.out.println("环绕后置通知: " + methodName);
        }

        return result;
    }
}
