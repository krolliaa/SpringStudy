package com.kk.pojo;

public class Student {
    private String name;
    private Integer age;
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

    public void setName(String name) {
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
                ", school=" + school.getName() + " " + school.getAddress() +
                '}';
    }
}