package com.zwm;

import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App11 {
    public static void main(String[] args) {
        String springConfig = "applicationContext11.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("myStudent");
        System.out.println(student.toString());
    }
}
