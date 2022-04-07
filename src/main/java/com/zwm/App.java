package com.zwm;

import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String springConfig = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeServiceImpl someService0 = (SomeServiceImpl) applicationContext.getBean("someService0");
        SomeServiceImpl someService1 = (SomeServiceImpl) applicationContext.getBean("someService1");
        someService0.doSome();
        someService1.doSome();
        Date date = (Date) applicationContext.getBean("date");
        System.out.println(date.toString());
    }
}
