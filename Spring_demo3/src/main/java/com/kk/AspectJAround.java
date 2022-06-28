package com.kk;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectJAround {
    @Around("execution(* com.kk.SomeServiceImpl.*(..))")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        System.out.println("环绕通知获取全类名：" + proceedingJoinPoint.getSignature());
        System.out.println("环绕通知获取方法名：" + proceedingJoinPoint.getSignature().getName());
        System.out.print("环绕通知获取参数名：");
        Object[] args = proceedingJoinPoint.getArgs();
        if (args != null && args.length > 0) {
            Arrays.stream(args).forEach(System.out::print);
            result = (Integer) proceedingJoinPoint.proceed(args);
            result = 100000;
        }
        return result;
    }
}

class AspectJAroundTest {
    //环绕通知：@Around 测试类
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeService someService = (SomeService) applicationContext.getBean("someServiceImpl");
        Integer integer = someService.doOther(1, 2);
        System.out.println("最后返回的结果：" + integer);
    }
}