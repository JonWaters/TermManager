package com.jonathanwaters.termmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Query("SELECT * FROM course")
    List<Course> getAll();

    @Query("SELECT * FROM course WHERE id = :id")
    Course getByID(int id);

    @Query("SELECT COUNT(id) FROM course")
    int count();

    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Query("UPDATE course SET term_id = :termID WHERE id = :courseID")
    void updateTermID(int courseID, int termID);

    @Delete
    void delete(Course course);
}
