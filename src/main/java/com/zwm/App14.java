package com.zwm;

import com.zwm.service.SomeService;
import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App14 {
    public static void main(String[] args) {
        String springConfig = "applicationContext14.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeService someService = (SomeServiceImpl) applicationContext.getBean("someServiceImpl");
        someService.doSome("ABC", 3);
    }
}
