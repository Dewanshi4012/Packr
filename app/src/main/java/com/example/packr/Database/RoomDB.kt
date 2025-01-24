package com.example.packr.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.packr.Dao.ItemsDao
import com.example.packr.Models.Items

@Database(entities = [Items::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun mainDao(): ItemsDao

    companion object {
        @Volatile
        private var database: RoomDB? = null
        private const val DATABASE_NAME = "MyDb"

        fun getInstance(context: Context): RoomDB {
            return database ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                database = instance
                instance
            }
        }
    }
}