package com.zwm;

import com.zwm.pojo.User;
import com.zwm.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class App21 {
    public static void main(String[] args) throws IOException {
        String springConfig = "applicationContext19.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        UserService userService = (UserService) applicationContext.getBean("userService");
        List<User> userList = userService.selectAllUsers();
        for(User user : userList) System.out.println(user.toString());
    }
}
