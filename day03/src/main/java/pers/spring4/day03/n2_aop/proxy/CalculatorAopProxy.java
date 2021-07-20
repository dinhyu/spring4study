package pers.spring4.day03.n2_aop.proxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
/*
 * 切入点表达式的写法；
 * 固定格式： execution(访问权限符  返回值类型  方法全类名(参数表))
 *   
 * 通配符：
 * 		*：
 * 			1）匹配一个或者多个字符:execution(public int com.atguigu.impl.MyMath*r.*(int, int))
 * 			2）匹配任意一个参数：第一个是int类型，第二个参数任意类型；（匹配两个参数）
 * 				execution(public int com.atguigu.impl.MyMath*.*(int, *))
 * 			3）只能匹配一层路径
 * 			4）权限位置*不能；权限位置不写就行；public【可选的】
 * 		..：
 * 			1）匹配任意多个参数，任意类型参数
 * 			2）匹配任意多层路径:
 * 				execution(public int com.atguigu..MyMath*.*(..));
 * 
 * 记住两种；
 * 最精确的：execution(public int com.atguigu.impl.MyMathCalculator.add(int,int))
 * 最模糊的：execution(* *.*(..))：千万别写；
 * 
 * &&”、“||”、“!
 * 
 * &&：我们要切入的位置满足这两个表达式
 * 	MyMathCalculator.add(int,double)
 * execution(public int com.atguigu..MyMath*.*(..))&&execution(* *.*(int,int))
 * 
 * 
 * ||:满足任意一个表达式即可
 * execution(public int com.atguigu..MyMath*.*(..))&&execution(* *.*(int,int))
 * 
 * !：只要不是这个位置都切入
 * !execution(public int com.atguigu..MyMath*.*(..))
 * 
 * 告诉Spring这个result用来接收返回值：
 * 	returning="result"；
 * 
 * 我们可以在通知方法运行的时候，拿到目标方法的详细信息
 * 1）只需要为通知方法的参数列表上写一个参数：
 *      JoinPoint joinPoint：封装了当前目标方法的详细信息
 * 2）告诉Spring哪个参数是用来接收异常
 *      throwing="exception"：告诉Spring哪个参数是用来接收异常
 * 3）Exception exception:指定通知方法可以接收哪些异常
 * 
 */
@Component
@Aspect
public class CalculatorAopProxy {
    //相同切入点抽取
    @Pointcut(value = "execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))")
    public void point(){}

    @Before(value = "point()")
    public void before(JoinPoint joinPoint){
        System.out.println("前置通知: " + joinPoint);
    }

    @AfterReturning(value = "execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))",returning = "result")
    public void afterRunning(JoinPoint joinPoint,Object result){
        System.out.println("返回通知: " + joinPoint + ",result: " + result);
    }

    @After("execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))")
    public void after(JoinPoint joinPoint){
        System.out.println("后置通知: " + joinPoint);
    }

    @AfterThrowing(value = "execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))",throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint,Exception exception){
        System.out.println("异常通知: " + joinPoint + ",exception: " + exception.getMessage());
    }

    /**
	 * @throws Throwable 
	 * @Around：环绕	:是Spring中强大的通知；
	 * @Around：环绕:动态代理；
	 * 	try{
	 * 			//前置通知
	 * 			method.invoke(obj,args);
	 * 			//返回通知
	 * 	}catch(e){
	 * 			//异常通知
	 *  }finally{
	 * 			//后置通知
	 * 	}
	 * 		
	 * 	四合一通知就是环绕通知；
	 * 	环绕通知中有一个参数：	ProceedingJoinPoint pjp
	 * 
	 *环绕通知：是优先于普通通知执行，执行顺序；
	 *
	 *[普通前置]
	 *{
	 *	try{
	 *		环绕前置
	 *		环绕执行：目标方法执行
	 *		环绕返回
	 *	}catch(){
	 *		环绕出现异常
	 *	}finally{
	 *		环绕后置
	 *	}
	 *}
	 *
	 *
	 *[普通后置]
	 *[普通方法返回/方法异常]
	 *新的顺序：
	 *		（环绕前置---普通前置）----目标方法执行----环绕正常返回/出现异常-----环绕后置----普通后置---普通返回或者异常
	 *注意：
	 */
    @Around(value = "point()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        //获取调用目标方法时传入的参数
        Object[] args = pjp.getArgs();
        //获取目标方法名
        String methodName = pjp.getSignature().getName();

        Object proceed = null;
        try {
            System.out.println("环绕前置通知: " + methodName + "方法开始");

            //利用反射调用目标方法:method.invoke(obj,args)
            proceed = pjp.proceed(args);

            System.out.println("环绕返回通知: " + methodName + "方法返回");
        } catch (Exception e) {
            System.out.println("环绕异常通知: " + methodName + "方法异常: " + e.getMessage());

            //为了让外界能知道这个异常，这个异常一定抛出去
            throw new RuntimeException(e);
        }finally{
            System.out.println("环绕后置通知: " + methodName + "方法结束");
        }

        //调用方法后的返回值需要返回出去
        return proceed;
    }
}
