package com.andyho.sampleapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class ImageData(
                    @PrimaryKey
                    val title: String,
                     @TypeConverters(StringArrayConverter::class)
                     val image:ArrayList<String>)