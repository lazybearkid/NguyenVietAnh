package com.example.androidexcercise.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidexcercise.Student


@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentRoomDatabase: RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StudentRoomDatabase? = null

        fun getDatabase(context: Context): StudentRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentRoomDatabase::class.java,
                    "student_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}