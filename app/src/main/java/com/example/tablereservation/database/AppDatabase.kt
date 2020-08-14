package com.example.tablereservation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tablereservation.dao.CustomerDao
import com.example.tablereservation.model.Customer

@Database(entities = [(Customer::class)], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun customerDao() : CustomerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}