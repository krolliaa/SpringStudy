package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "mySchool")
public class School {
    private String schoolName;
    private String schoolAddress;

    public School() {
    }

    public School(String schoolName, String schoolAddress) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolName() {
        return schoolName;
    }

    @Value(value = "华南理工大学")
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    @Value(value = "广东省广州市天河区五山路381号")
    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolName='" + schoolName + '\'' +
                ", schoolAddress='" + schoolAddress + '\'' +
                '}';
    }
}
