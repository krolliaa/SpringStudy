package com.zwm.service.impl;

import com.zwm.service.SomeService;

public class SomeServiceImpl implements SomeService {

    public SomeServiceImpl() {
        System.out.println("执行 SomeServiceImpl 中的无参构造方法");
    }

    @Override
    public void doSome() {
        System.out.println("执行 SomeServiceImpl 中的 doSome() 方法");
    }
}
