package com.key.imageio.aop.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 定义拦截方法执行前动作
 *
 * @author tujia
 * @since 2020/6/22 9:20
 */
public class LogArgsAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {

        System.out.println("准备执行方法：" + method.getName() + ",参数列表：" + Arrays.toString(args));
    }
}
