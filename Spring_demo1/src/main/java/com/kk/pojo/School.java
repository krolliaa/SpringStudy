package com.kk.pojo;

public class School {
    private String name;
    private String address;

    public School() {
        System.out.println("调用了 School 类的无参构造方法！");
    }

    public School(String name, String address) {
        System.out.println("调用了 School 类的有参构造方法！");
        this.name = name;
        this.address = address;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
