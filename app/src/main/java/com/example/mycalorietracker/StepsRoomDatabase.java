package com.example.mycalorietracker;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities={StepsRoom.class},version = 2,exportSchema = false)
public abstract class StepsRoomDatabase extends RoomDatabase {

    public abstract StepsRoomDao StepsRoomDao();
    private static volatile StepsRoomDatabase INSTANCE;
    static StepsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StepsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),StepsRoomDatabase.class, "customer_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
