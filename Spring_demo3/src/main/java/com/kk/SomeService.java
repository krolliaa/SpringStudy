package com.kk;

import org.springframework.stereotype.Component;

@Component(value = "someService")
public interface SomeService {
    public abstract void doSome();
    public abstract Integer doOther(Integer a, Integer b);
}
