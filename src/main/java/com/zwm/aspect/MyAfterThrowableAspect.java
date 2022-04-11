package com.zwm.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAfterThrowableAspect {
    @AfterThrowing(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))", throwing = "throwable")
    public void myAfterThrowable(Throwable throwable) throws InterruptedException {
        System.out.println("异常通知，方法执行抛出异常之前执行：" + throwable.getMessage());
        Thread.sleep(3000);
    }
}
