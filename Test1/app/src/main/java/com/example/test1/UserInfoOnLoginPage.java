package com.example.test1;

import android.net.Uri;

public class UserInfoOnLoginPage
{
    private String name , phone_no;
    private String url;
    UserInfoOnLoginPage()
    {
    }

    UserInfoOnLoginPage(String name, String phone_no)
    {
        this.name = name;
        this.phone_no = phone_no;
    }


    UserInfoOnLoginPage(String name, String phone_no , String url)
    {
        this.name = name;
        this.phone_no = phone_no;
        this.url = url;
    }

    public String getUri() {
        return url;
    }

    public void setUri(String uri) {
        this.url = uri;
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
