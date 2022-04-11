package com.zwm.service.impl;

import com.zwm.pojo.Student;
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
    public Student doSome(String name, int age) {
        int a = 0;
        System.out.println(1 / a);
        System.out.println("========SomeService的doSome()方法========");
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return student;
    }

    @Override
    public void doOther() {
        System.out.println("========SomeService的doOther()方法========");
    }
}
