package com.zwm;

import com.zwm.service.SomeService;
import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App17 {
    public static void main(String[] args) {
        String springConfig = "applicationContext17.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeService someService = (SomeServiceImpl) applicationContext.getBean("someServiceImpl");
        Object result = (Object) someService.doSome("ABC", 3);
        System.out.println(result);
    }
}
