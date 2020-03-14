package com.example.test1;

public class ModelClass_RentPage {
    String name , phone_no , uri;

    ModelClass_RentPage(){}



    public ModelClass_RentPage(String name, String phone_no, String uri) {
        this.name = name;
        this.phone_no = phone_no;
        this.uri = uri;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
