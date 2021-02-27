package com.jonathanwaters.termmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Query("SELECT * FROM assessment")
    List<Assessment> getAll();

    @Query("SELECT * FROM assessment WHERE id = :id")
    Assessment getByID(int id);

    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Query("UPDATE assessment SET course_id = :courseID WHERE id = :assessmentID")
    void updateCourseID(int assessmentID, int courseID);

    @Delete
    void delete(Assessment assessment);
}
