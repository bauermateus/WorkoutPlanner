package com.mbs.workoutplanner.dataBase

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun insertUserInfo(info: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPhoto(photo: UserPhotoEntity)

    @Update
    suspend fun updateUserPhoto(photo: UserPhotoEntity)

    @Query("SELECT * FROM ${DatabaseConstants.UserPhotoDataBaseName} WHERE uuid = 1")
    suspend fun getUserPhoto(): UserPhotoEntity?
}