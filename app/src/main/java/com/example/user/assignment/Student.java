package com.example.user.assignment;

/**
 * Created by Jin Hui on 21-Dec-16.
 */

public class Student {

    private String studId;
    private String name;
    private String password;
    private String email;
    private String telNo;

    public Student() {

    }

    public Student(String studId, String name, String password, String email, String telNo) {
        this.studId = studId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.telNo = telNo;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getStudId() {
        return studId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getTelNo() {
        return telNo;
    }

}


