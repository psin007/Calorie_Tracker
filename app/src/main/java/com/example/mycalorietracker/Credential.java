package com.example.mycalorietracker;

import java.util.Collection;
import java.util.Date;

public class Credential {

    private String username;
    private Integer userid;
    private String passwordhash;
    private Date signupdate;


//    public Credential() {
//    }
//
//    public Credential(Integer userid) {
//        this.userid = userid;
//    }
//
//    public Integer getUserid() {
//        return userid;
//    }
//
//    public void setUserid(Integer userid) {
//        this.userid = userid;
//    }


    public Credential() {
    }

    public Credential(String username, Integer userid, String passwordhash, Date signupdate) {
        this.username = username;
        this.userid = userid;
        this.passwordhash = passwordhash;
        this.signupdate = signupdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public Date getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }
}
