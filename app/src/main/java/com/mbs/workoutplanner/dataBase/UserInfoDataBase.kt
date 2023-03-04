package com.mbs.workoutplanner.dataBase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserInfoEntity::class, UserPhotoEntity::class], version = 1, exportSchema = false)
abstract class UserInfoDataBase: RoomDatabase() {

    abstract fun userInfoDao(): UserDao

    companion object {
        private var instance: UserInfoDataBase? = null

        fun getInstance(context: Context): UserInfoDataBase {
            if (instance == null) {
                synchronized(UserInfoDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserInfoDataBase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance!!
        }
        private const val DATABASE_NAME = "userinfo-database.db"
    }
}