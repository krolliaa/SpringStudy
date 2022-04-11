package com.zwm.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAfterAspect {
    @After(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public void myAfterThrowable() {
        System.out.println("最终通知，最后且总是会执行的通知");
    }
}
