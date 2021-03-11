package com.jonathanwaters.termmanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Query("SELECT * FROM alarm")
    List<AlarmID> getAll();

    @Insert
    void insert(AlarmID alarmID);
}
