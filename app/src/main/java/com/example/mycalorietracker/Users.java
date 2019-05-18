package com.example.mycalorietracker;

import java.util.Date;

public class Users {
    private Integer userid;
    private String name;
    private String surname;
    private String email;
    private Date dob;
    private Double height;
    private Double weight;
    private Character gender;
    private String address;
    private Integer postcode;
    private int levelofactivity;
    private Double stepspermile;

    public Users() {
    }

    public Users(Integer userid, String name, String surname, String email, Date dob, Double height, Double weight, Character gender, String address, Integer postcode, int levelofactivity, Double stepspermile) {
        this.userid = userid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelofactivity = levelofactivity;
        this.stepspermile = stepspermile;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public int getLevelofactivity() {
        return levelofactivity;
    }

    public void setLevelofactivity(int levelofactivity) {
        this.levelofactivity = levelofactivity;
    }

    public Double getStepspermile() {
        return stepspermile;
    }

    public void setStepspermile(Double stepspermile) {
        this.stepspermile = stepspermile;
    }


}
