package me.ancyphilip.womentor.Models;

public class UserModel {

    public UserModel(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public UserModel() {
    }

    private String name;

    private String phone;

    private String email;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
