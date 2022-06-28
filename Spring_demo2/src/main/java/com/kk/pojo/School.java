package com.kk.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "mySchool")
public class School {
    @Value(value = "华南理工大学")
    private String name;
    @Value(value = "广州市天河区五山")
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
