package com.zwm.aspect;

import com.zwm.pojo.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
//@Component
public class MyAfterReturningAspect {
    @AfterReturning(value = "execution(* com.zwm.service.impl.SomeServiceImpl.*(..))", returning = "student")
    public void myAfterReturning(JoinPoint joinPoint, Student student) {
        System.out.println("该方法的全类名 - 后置通知获取：" + joinPoint.getSignature());
        System.out.println("该方法的方法名 - 后置通知获取：" + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                System.out.println("该方法的形式参数：" + arg);
            }
        }
        System.out.println("后置通知：doSome()返回参数值后打印当前时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("在后置通知执行方法之前的 student 数据：" + student.toString());
        student.setName("DEF");
        System.out.println("在后置通知执行方法之前的 student 数据：" + student.toString());
    }
}
