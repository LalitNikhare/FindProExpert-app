package com.example.com.findproexperttabbed;

/**
 * Created by faltu on 10-Feb-17.
 */

public class Actor {
    String fname,lname,address,email,dob,mobile,user,pass1;
    public Actor(){

    }

    public Actor(String fname, String lname, String address, String email, String dob, String mobile, String user, String pass1) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.email = email;
        this.dob = dob;
        this.mobile = mobile;
        this.user = user;
        this.pass1 = pass1;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
