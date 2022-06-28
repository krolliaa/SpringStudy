package com.kk;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

//@Aspect
@Component
public class AspectJAfterReturning {
    @AfterReturning(value = "execution(* com.kk.SomeServiceImpl.*(..))", returning = "number")
    public void myAfterReturning(JoinPoint joinPoint, Integer number) {
        System.out.println("后置通知获取全类名：" + joinPoint.getSignature());
        System.out.println("后置通知所有方法名：" + joinPoint.getSignature().getName());
        System.out.print("后置通知全形式参数：");
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(System.out::print);
        System.out.println();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        System.out.println("执行目标方法之后，记录执行之后的时间：" + date);
        System.out.println("返回的数字：" + number);
        number = Integer.valueOf(100);
    }
}

class AspectJAfterReturningTest {
    //后置通知：@After 测试类
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService someService = (SomeService) applicationContext.getBean("someServiceImpl");
        Integer integer = someService.doOther(1, 2);
        System.out.println("最后返回的结果：" + integer);
    }
}