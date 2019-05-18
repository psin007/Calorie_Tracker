package com.example.mycalorietracker;

import java.util.Collection;

public class Food {
    private Integer foodid;
    private String name;
    private String category;
    private Double calorieamount;
    private String servingunit;
    private Double servingamount;
    private Double fat;
    private Collection<Consumption> consumptionCollection;

    public Food() {
    }

    public Food(Integer foodid, String name, String category, Double calorieamount, String servingunit, Double servingamount, Double fat, Collection<Consumption> consumptionCollection) {
        this.foodid = foodid;
        this.name = name;
        this.category = category;
        this.calorieamount = calorieamount;
        this.servingunit = servingunit;
        this.servingamount = servingamount;
        this.fat = fat;
        this.consumptionCollection = consumptionCollection;
    }

    public Food(Integer foodid, String name, String category, Double calorieamount, String servingunit, Double servingamount, Double fat) {
        this.foodid = foodid;
        this.name = name;
        this.category = category;
        this.calorieamount = calorieamount;
        this.servingunit = servingunit;
        this.servingamount = servingamount;
        this.fat = fat;
    }

    public Integer getFoodid() {
        return foodid;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getCalorieamount() {
        return calorieamount;
    }

    public void setCalorieamount(Double calorieamount) {
        this.calorieamount = calorieamount;
    }

    public String getServingunit() {
        return servingunit;
    }

    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }

    public Double getServingamount() {
        return servingamount;
    }

    public void setServingamount(Double servingamount) {
        this.servingamount = servingamount;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Collection<Consumption> getConsumptionCollection() {
        return consumptionCollection;
    }

    public void setConsumptionCollection(Collection<Consumption> consumptionCollection) {
        this.consumptionCollection = consumptionCollection;
    }
}
