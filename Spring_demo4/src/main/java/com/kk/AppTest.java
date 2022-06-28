package com.kk;

import com.kk.pojo.Account;
import com.kk.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class AppTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = (AccountService) applicationContext.getBean("accountServiceImpl");
        List<Account> accounts = accountService.findAllAccounts();
        accounts.forEach(System.out::println);
    }
}
