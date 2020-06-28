package com.key.imageio.aop.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 定义拦截方法返回后动作
 *
 * @author tujia
 * @since 2020/6/22 9:21
 */
public class LogReturnAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

        System.out.println("方法返回：" + returnValue);
    }
}
