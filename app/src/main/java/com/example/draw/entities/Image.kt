package com.example.draw.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "url")
    val url: String
) : java.io.Serializable