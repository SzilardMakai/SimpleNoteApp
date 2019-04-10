package com.szilardmakai.simplenoteapp.database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes_table LIMIT 1")
    Note[] getAnyNote();

    @Query("SELECT * FROM notes_table ORDER BY modified_date DESC")
    DataSource.Factory<Integer, Note> getAllNotes();
}