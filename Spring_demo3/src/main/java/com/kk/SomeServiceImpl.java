package com.kk;

import org.springframework.stereotype.Component;

@Component(value = "someServiceImpl")
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("做一些事情...");
    }

    @Override
    public Integer doOther(Integer a, Integer b) {
        System.out.println("\n做一些其它事情...");
        Integer integer = new Integer(a + b);
        return integer;
    }
}
