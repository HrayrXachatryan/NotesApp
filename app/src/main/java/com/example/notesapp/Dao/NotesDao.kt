package com.example.notesapp.dao

import androidx.room.*
import com.example.notesapp.entity.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Notes>

    @Insert
    suspend fun insert(note: Notes): Long

    @Update
    suspend fun update(note: Notes)

    @Delete
    suspend fun delete(note: Notes)
}
