package com.jonathanwaters.termmanager;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class, AlarmID.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    private static final String DB_NAME = "tm_db";
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract AlarmDAO alarmDAO();
}
