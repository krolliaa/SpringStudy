package com.kk;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectJAfter {
    @After(value = "execution(* com.kk.SomeServiceImpl.*(..))")
    public void after(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature());
        System.out.println("无论如何都会执行的通知@After");
    }
}

class AspectJAfterTest {
    //最终通知：@After 测试类
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService someService = (SomeService) applicationContext.getBean("someServiceImpl");
        Integer integer = someService.doOther(1, 2);
        System.out.println("最后返回的结果：" + integer);
    }
}