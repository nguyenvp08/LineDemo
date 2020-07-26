package com.andyho.sampleapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.andyho.sampleapplication.model.DownloadData
import com.andyho.sampleapplication.model.ImageData
import com.andyho.sampleapplication.model.StringArrayConverter

@Database(entities = [ImageData::class, DownloadData::class], version = 1)
@TypeConverters(StringArrayConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract val dao: AppDao?

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppRoomDatabase {
            return Room.databaseBuilder(context, AppRoomDatabase::class.java, "app_room")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}