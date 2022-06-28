package com.kk.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "student")
public class Student {
    @Value(value = "张三")
    private String name;
    @Value(value = "100")
    private Integer age;
    @Autowired
    @Qualifier(value = "mySchool")
    private School school;

    public Student() {
        System.out.println("调用了 Student 类的无参构造器！");
    }

    public Student(String name, Integer age, School school) {
        System.out.println("调用了 Student 类的有参构造器！");
        this.name = name;
        this.age = age;
        this.school = school;

    }

    public String getName() {
        return name;
    }

    public void setName1(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school.getName() +
                '}';
    }
}