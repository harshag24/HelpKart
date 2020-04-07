package com.example.test1;


public class Model_Class
{
    private String name , phone_no , brand , timeUsed , desc , url , email ,price , isSeller;

    public Model_Class(String isSeller) {
        this.isSeller = isSeller;
    }

    public Model_Class(String name, String phone_no) {
        this.name = name;
        this.phone_no = phone_no;
    }


    public Model_Class(String name, String phone_no, String brand, String timeUsed, String desc, String url, String price) {
        this.name = name;
        this.phone_no = phone_no;
        this.brand = brand;
        this.timeUsed = timeUsed;
        this.desc = desc;
        this.url = url;
        this.price = price;
    }

    public Model_Class() {
    }


    public String getIsSeller() { return isSeller; }

    public void setIsSeller(String isSeller) { this.isSeller = isSeller; }

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
