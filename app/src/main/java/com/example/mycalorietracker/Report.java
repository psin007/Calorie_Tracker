package com.example.mycalorietracker;

import java.util.Date;

public class Report {
    private int userid;
    private Date Reportdate;
    private double totalcaloriesconsumed;
    private double totalcaloriesburned;
    private int totalsteps;
    private double setgoal;
    private int reportid;

    public Report() {
    }

    public Report(int userid, Date reportdate, double totalcaloriesconsumed, double totalcaloriesburned, int totalsteps, double setgoal, int reportid) {
        this.userid = userid;
        Reportdate = reportdate;
        this.totalcaloriesconsumed = totalcaloriesconsumed;
        this.totalcaloriesburned = totalcaloriesburned;
        this.totalsteps = totalsteps;
        this.setgoal = setgoal;
        this.reportid = reportid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getReportdate() {
        return Reportdate;
    }

    public void setReportdate(Date reportdate) {
        Reportdate = reportdate;
    }

    public double getTotalcaloriesconsumed() {
        return totalcaloriesconsumed;
    }

    public void setTotalcaloriesconsumed(double totalcaloriesconsumed) {
        this.totalcaloriesconsumed = totalcaloriesconsumed;
    }

    public double getTotalcaloriesburned() {
        return totalcaloriesburned;
    }

    public void setTotalcaloriesburned(double totalcaloriesburned) {
        this.totalcaloriesburned = totalcaloriesburned;
    }

    public int getTotalsteps() {
        return totalsteps;
    }

    public void setTotalsteps(int totalsteps) {
        this.totalsteps = totalsteps;
    }

    public double getSetgoal() {
        return setgoal;
    }

    public void setSetgoal(double setgoal) {
        this.setgoal = setgoal;
    }

    public int getReportid() {
        return reportid;
    }

    public void setReportid(int reportid) {
        this.reportid = reportid;
    }
}
