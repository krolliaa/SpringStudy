package com.zwm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class MyBeforeAspect {
    @Before(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public void myBefore(JoinPoint joinPoint) {
        System.out.println("前置通知获取方法全类名：" + joinPoint.getSignature());
        System.out.println("前置通知获取方法名：" + joinPoint.getSignature().getName());
        System.out.println("前置通知获取方法参数：");
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) System.out.println(arg);
        }
        System.out.println("前置通知，输出执行目标方法之前的时间：" + new Date());
    }
}
