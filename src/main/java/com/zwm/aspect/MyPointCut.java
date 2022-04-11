package com.zwm.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyPointCut {
    @AfterThrowing(value = "myPointcut()")
    public void myAfterThrowing() {
        System.out.println("最终通知：最终总是会执行的方法");
    }

    @Pointcut(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public void myPointcut() {
    }
}
