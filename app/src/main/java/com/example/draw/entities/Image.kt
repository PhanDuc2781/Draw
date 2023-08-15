package com.example.draw.entities

import android.os.Parcelable
import android.text.PrecomputedText.Params
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "image")
data class Image(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @Transient
    var id: Long,

    @ColumnInfo(name = "url")
    val url: String
) : Parcelable