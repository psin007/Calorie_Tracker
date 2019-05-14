package com.example.mycalorietracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class StepsRoom {
    @PrimaryKey(autoGenerate = true)
    public int sid; //id of row steps is entered

    @ColumnInfo(name="time_Stamp")
    public String timeStamp; //time steps is entered

    @ColumnInfo(name="steps_taken")
    public int stepsTaken;

    public StepsRoom(String timeStamp, int stepsTaken) {
        this.timeStamp = timeStamp;
        this.stepsTaken = stepsTaken;
    }

    public StepsRoom() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public void setStepsTaken(int stepsTaken) {
        this.stepsTaken = stepsTaken;
    }
}
