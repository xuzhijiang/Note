package com.atguigu.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面类
 * @author lfy
 * 
 * @Aspect： 告诉Spring当前类是一个切面类
 */
@Aspect
public class LogAspects {
	
	//抽取公共的切入点表达式(最后一个*表示MathCalculator的所有方法,最后2个点表示任意类型的任意参数)
	//1、本类引用这个切入点表达式: pointCut()
	//2、其他的切面引用这个切入点表达式(全类名路径): com.atguigu.aop.LogAspects.pointCut()
	@Pointcut("execution(public int com.atguigu.aop.MathCalculator.*(..))")
	public void pointCut(){};
	
	//@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
	@Before("pointCut()")
	public void logStart(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		System.out.println(""+joinPoint.getSignature().getName()+"运行。。。@Before:参数列表是：{"+Arrays.asList(args)+"}");
	}
	
	@After("com.atguigu.aop.LogAspects.pointCut()")
	public void logEnd(JoinPoint joinPoint){
		System.out.println(""+joinPoint.getSignature().getName()+"结束。。。@After");
	}
	
	//JoinPoint一定要出现在参数表的第一位,否则spring也是无法识别的.
	@AfterReturning(value="pointCut()",returning="result") // returning来指定logReturn这个方法的哪个参数来接受返回值.,这里就是result来接受返回值.也就是result最终会把目标方法的返回值封装.
	public void logReturn(JoinPoint joinPoint,Object result){
		System.out.println(""+joinPoint.getSignature().getName()+"正常返回。。。@AfterReturning:运行结果：{"+result+"}");
	}

	// @AfterThrowing的参数解释: 告诉spring这个throwing中配置的值exception是来接目标方法抛出的异常的,
	// 一定要执行,不然logException方法的参数突然冒出一个exception,spring也不知道要干什么.
	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(JoinPoint joinPoint,Exception exception){
		System.out.println(""+joinPoint.getSignature().getName()+"异常。。。异常信息：{"+exception+"}");
	}

}
