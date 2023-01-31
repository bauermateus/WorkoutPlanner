package com.mbs.workoutplanner.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [WorkoutEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object {
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(AppDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance!!
        }
        private const val DATABASE_NAME = "app-database.db"
    }
}