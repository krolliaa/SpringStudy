package com.kk;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

//@Aspect
@Component(value = "aspectJBefore")
public class AspectJBefore {
    @Before(value = "execution(* com.kk.SomeServiceImpl.*(..))")
    public void myBefore(JoinPoint joinPoint) {
        System.out.println("AspectJ 前置通知获取全类名：" + joinPoint.getSignature());
        System.out.println("AspectJ 前置通知获取方法名：" + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(System.out::println);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        System.out.println("开始执行目标方法，记录执行时间：" + date);
    }
}

class AspectJBeforeTest {
    //前置通知：@Before 测试类
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService someService = (SomeService) applicationContext.getBean("someServiceImpl");
        someService.doSome();
        someService.doOther(1, 2);
    }
}
