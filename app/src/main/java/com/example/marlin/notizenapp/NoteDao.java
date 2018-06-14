package com.example.marlin.notizenapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Marlin on 04.02.2018.
 * Initialisierung des DataAccessObjects
 */

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE id = :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM note WHERE notiz LIKE :note")
    int getIdbyNote(String note);

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Update
    void bearbeite(Note note);

}
