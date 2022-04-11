package com.zwm;

import com.zwm.pojo.Student;
import com.zwm.service.SomeService;
import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App15 {
    public static void main(String[] args) {
        String springConfig = "applicationContext15.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeService someService = (SomeServiceImpl) applicationContext.getBean("someServiceImpl");
        Student student = someService.doSome("ABC", 3);
        System.out.println("执行 doSome 方法返回的数据：" + student.toString());
    }
}
