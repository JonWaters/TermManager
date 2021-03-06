package com.jonathanwaters.termmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDAO {
    @Query("SELECT * FROM term")
    List<Term> getAll();

    @Query("SELECT * FROM term WHERE id = :id")
    Term getByID(int id);

    @Query("SELECT COUNT(id) FROM term")
    int count();

    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);
}
