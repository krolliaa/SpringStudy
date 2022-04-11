package com.zwm.service;

import com.zwm.pojo.Student;

public interface SomeService {
    public abstract Student doSome(String name, int age);

    public abstract void doOther();
}
