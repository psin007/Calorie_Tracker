package com.example.mycalorietracker;

import java.util.Collection;
import java.util.Date;

public class Credential {

    private Integer userid;

    public Credential() {
    }

    public Credential(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
