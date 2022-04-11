package com.zwm.aspect;

import com.zwm.pojo.Student;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class MyAroundAspect {
    @Around(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public Object MyAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        System.out.println("环绕通知方法执行前的时间：" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println("环绕通知获取全类名：" + proceedingJoinPoint.getSignature());
        System.out.println("环绕通知获取方法名：" + proceedingJoinPoint.getSignature().getName());
        Object[] args = proceedingJoinPoint.getArgs();
        String name = "";
        if (args != null && args.length > 0) {
            for (Object arg : args) System.out.println("环绕通知获取方法形式参数：" + arg);
            name = (String) args[0];
        }
        if ("ABC".equals(name)) {
            System.out.print("环绕通知执行方法，返回方法执行结果保存在 result 中：");
            result = proceedingJoinPoint.proceed(args);
        }
        System.out.print("方法执行完毕，可以修改方法的返回结果：");
        if (result != null && result instanceof Student) ((Student) result).setName("DEF");
        return result;
    }
}