package com.example.draw.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.draw.entities.Image

@Dao
interface DAO {

    @Query("SELECT * FROM image")
    fun getAll() : LiveData<MutableList<Image>>

    @Insert
    suspend fun insert(image: Image)

    @Delete
    suspend fun delete(image: Image)

    @Query("SELECT * FROM image WHERE id = :ID")
    suspend fun getById(ID: Int) : Image
}