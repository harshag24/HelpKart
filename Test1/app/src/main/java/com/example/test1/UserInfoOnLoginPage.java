package com.example.test1;

public class UserInfoOnLoginPage {
    String name;
    String phone_no;
    UserInfoOnLoginPage()
    {

    }

    public UserInfoOnLoginPage(String name, String phone_no) {
        this.name = name;
        this.phone_no = phone_no;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
