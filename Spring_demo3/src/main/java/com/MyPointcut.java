package com;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyPointcut {

    @After(value = "myPointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature());
        System.out.println("无论如何都会执行的通知@After");
    }

    @Pointcut(value = "execution(* com.kk.SomeServiceImpl.*(..))")
    public void myPointcut() {
    }
}
