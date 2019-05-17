package com.example.mycalorietracker;

import java.util.Date;

public class Consumption {
    private Date consumptiondate;
    private Double quantity;
    private Integer consumptionid;
    private Credential userid;
    private Food foodid;

    public Consumption() {
    }

    public Consumption(Date consumptiondate, Double quantity, Integer consumptionid, Credential userid, Food foodid) {
        this.consumptiondate = consumptiondate;
        this.quantity = quantity;
        this.consumptionid = consumptionid;
        this.userid = userid;
        this.foodid = foodid;
    }

    public Date getConsumptiondate() {
        return consumptiondate;
    }

    public void setConsumptiondate(Date consumptiondate) {
        this.consumptiondate = consumptiondate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getConsumptionid() {
        return consumptionid;
    }

    public void setConsumptionid(Integer consumptionid) {
        this.consumptionid = consumptionid;
    }

    public Credential getUserid() {
        return userid;
    }

    public void setUserid(Credential userid) {
        this.userid = userid;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
    }
}
