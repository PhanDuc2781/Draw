package com.example.draw.di

import android.content.Context
import androidx.room.Room
import com.example.draw.dao.DAO
import com.example.draw.database.ImageDatabase
import com.example.draw.entities.Image
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provider {

    @Singleton
    @Provides
    fun imageDatabase(@ApplicationContext context: Context): ImageDatabase {
        return Room.databaseBuilder(context, ImageDatabase::class.java, "image")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun imageDao(imageDatabase: ImageDatabase): DAO {
        return imageDatabase.imageDao()
    }
}