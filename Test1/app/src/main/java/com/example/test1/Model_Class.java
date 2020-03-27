package com.example.test1;


public class Model_Class
{
    private String name , phone_no , Brand , timeUsed , Desc , url;

    Model_Class()
    {
    }

    Model_Class(String name, String phone_no, String Brand, String timeUsed, String Desc, String url)
    {   this.name = name;
        this.phone_no = phone_no;
        this.Brand = Brand;
        this.timeUsed = timeUsed;
        this.Desc = Desc;
        this.url = url; }

    Model_Class(String name, String phone_no)
    { this.name = name;
    this.phone_no = phone_no; }

    public String getBrand() { return Brand; }

    public void setBrand(String Brand) { this.Brand = Brand; }

    public String getTimeUsed() { return timeUsed; }

    public void setTimeUsed(String timeUsed) { this.timeUsed = timeUsed; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getDesc() { return Desc;}

    public void setDesc(String Desc) { this.Desc = Desc; }

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
