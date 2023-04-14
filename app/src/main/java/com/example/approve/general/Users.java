package com.example.approve.general;

public class Users {

    String userName, email, pass, type;

    public Users() {}

    public Users(String userName, String email, String pass, String type) {
        this.userName = userName;
        this.email = email;
        this.pass = pass;
        this.type = type;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getpass() {
        return pass;
    }

    public void setpass(String pass) {
        this.pass = pass;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }
}