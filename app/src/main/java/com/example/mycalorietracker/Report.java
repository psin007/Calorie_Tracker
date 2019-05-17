package com.example.mycalorietracker;

import java.util.Date;

public class Report {
    private Date reportdate;
    private Double totalcaloriesconsumed;
    private Double totalcaloriesburned;
    private Integer totalsteps;
    private Double setgoal;
    private Integer reportid;
    private Credential userid;

    public Report() {

    }


    public Report(Date reportdate, Double totalcaloriesconsumed, Double totalcaloriesburned, Integer totalsteps, Double setgoal, Integer reportid, Credential userid) {
        this.reportdate = reportdate;
        this.totalcaloriesconsumed = totalcaloriesconsumed;
        this.totalcaloriesburned = totalcaloriesburned;
        this.totalsteps = totalsteps;
        this.setgoal = setgoal;
        this.reportid = reportid;
        this.userid = userid;
    }

    public Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(Date reportdate) {
        this.reportdate = reportdate;
    }

    public Double getTotalcaloriesconsumed() {
        return totalcaloriesconsumed;
    }

    public void setTotalcaloriesconsumed(Double totalcaloriesconsumed) {
        this.totalcaloriesconsumed = totalcaloriesconsumed;
    }

    public Double getTotalcaloriesburned() {
        return totalcaloriesburned;
    }

    public void setTotalcaloriesburned(Double totalcaloriesburned) {
        this.totalcaloriesburned = totalcaloriesburned;
    }

    public Integer getTotalsteps() {
        return totalsteps;
    }

    public void setTotalsteps(Integer totalsteps) {
        this.totalsteps = totalsteps;
    }

    public Double getSetgoal() {
        return setgoal;
    }

    public void setSetgoal(Double setgoal) {
        this.setgoal = setgoal;
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public Credential getUserid() {
        return userid;
    }

    public void setUserid(Credential userid) {
        this.userid = userid;
    }
}
