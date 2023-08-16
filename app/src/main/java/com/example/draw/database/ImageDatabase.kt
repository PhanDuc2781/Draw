package com.example.draw.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.draw.dao.DAO
import com.example.draw.entities.Image

@Database(entities = [Image::class] , version = 3)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao() : DAO
}