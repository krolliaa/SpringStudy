package com.zwm.service.impl;

import com.zwm.service.SomeService;
import org.springframework.stereotype.Service;

@Service(value = "someServiceImpl")
public class SomeServiceImpl implements SomeService {

    public SomeServiceImpl() {
        System.out.println("执行 SomeServiceImpl 中的无参构造方法");
    }

    public void doSome() {
        System.out.println("执行 SomeServiceImpl 中的 doSome() 方法");
    }

    @Override
    public void doSome(String name, int age) {
        System.out.println("========SomeService的doSome()方法========");
    }

    @Override
    public void doOther() {
        System.out.println("========SomeService的doOther()方法========");
    }
}
