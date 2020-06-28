package com.key.imageio.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 切面模拟
 *
 * @author tujia
 * @since 2020/6/22 9:41
 */
@Aspect
@Component
public class AdviceConfig {


    // 应用@Pointcut定义
    @Before("com.key.imageio.aop.aspectj.SystemArchitecture.dataAccessOperation()")
    public void doAccessCheck() {

    }

    // 应用@Pointcut定义 -- 接收参数
    @Before("com.key.imageio.aop.aspectj.SystemArchitecture.dataAccessOperation()")
    public void doAccessCheckWithArg(JoinPoint joinPoint) {
        System.out.println("方法执行前，打印入参：" + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning("com.key.imageio.aop.aspectj.SystemArchitecture.dataAccessOperation()")
    public void doAccessCheckReturn() {

    }

    // 拦截异常情况
    @AfterThrowing(pointcut = "com.key.imageio.aop.aspectj.SystemArchitecture.dataAccessOperation()", throwing = "ex")
    public void doRecoveryActions(RuntimeException ex) {
        // ... 实现代码
    }

    // 拦截正常返回和异常的情况
    @After("com.key.imageio.aop.aspectj.SystemArchitecture.dataAccessOperation()")
    public void doReleaseLock() {

    }

    // 环绕通知，既能做 @Before 的事情，也可以做 @AfterReturning 的事情
    @Around("com.key.imageio.aop.aspectj.SystemArchitecture.businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint point) throws Throwable {
        System.out.println("进入了拦截方法");
        Object result = point.proceed();

        return result;
    }
}
