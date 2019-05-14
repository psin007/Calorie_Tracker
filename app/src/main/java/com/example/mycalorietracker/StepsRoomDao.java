package com.example.mycalorietracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsRoomDao {

    @Query("Select * from StepsRoom")
    List<StepsRoom> getAll();

    @Query("Select count(steps_taken) from StepsRoom")
    int totalSteps();

    @Query("Select * from StepsRoom where sid = :searchid LIMIT 1")
    StepsRoom findByID(int searchid);

    @Query("Select sid from StepsRoom where time_Stamp=:timeStamp")
    int findId(String timeStamp);

    @Insert
    void insert(StepsRoom stepsRoom);

    @Delete
    void delete(StepsRoom stepsRoom);

    @Update(onConflict = REPLACE)
    public void updateSteps(StepsRoom... stepsRooms);





}
