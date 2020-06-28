package com.key.imageio.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author tujia
 * @since 2020/6/22 9:33
 */
@Aspect
@Component
public class SystemArchitecture {


    @Pointcut("within(com.key.imageio.web.controller..*)")
    public void inWebLayer() {
    }

    @Pointcut("within(com.key.imageio.web.service..*)")
    public void inServiceLayer() {
    }

    @Pointcut("within(com.key.imageio.web.dao..*)")
    public void inDataAccessLayer() {
    }

    //    @Pointcut("bean(*ServiceImpl)")
    @Pointcut("execution(* com.key.imageio.web.service.*.*(..))")
    public void businessService() {
    }


    @Pointcut("execution(* com.key.imageio.web.dao.*.*(..))")
    public void dataAccessOperation() {
    }

    @Around("inWebLayer()")
    public Object enhance(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        return result;
    }
}
