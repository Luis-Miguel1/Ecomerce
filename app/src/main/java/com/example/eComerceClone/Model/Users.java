package com.example.eComerceClone.Model;

public class Users {
    private String name,phone,passWord;

    public Users(String name, String phone, String passWord) {
        this.name = name;
        this.phone = phone;
        this.passWord = passWord;
    }

    public Users() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
