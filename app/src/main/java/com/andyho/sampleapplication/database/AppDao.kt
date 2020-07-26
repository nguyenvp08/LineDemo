package com.andyho.sampleapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andyho.sampleapplication.model.DownloadData
import com.andyho.sampleapplication.model.ImageData

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ImageData): Long

    @Query("select * from ImageData limit 1")
    fun getImageData(): ImageData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: DownloadData): Long

    @Query("select * from DownloadData where image=:image")
    fun getDownloadData(image: String) : DownloadData?
}