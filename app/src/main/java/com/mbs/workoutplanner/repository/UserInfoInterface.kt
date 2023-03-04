package com.mbs.workoutplanner.repository

import com.mbs.workoutplanner.dataBase.UserInfoEntity
import com.mbs.workoutplanner.dataBase.UserPhotoEntity

interface UserInfoInterface {
    suspend fun insertUserInfo(info: UserInfoEntity)
    suspend fun insertUserPhoto(photo: UserPhotoEntity)
    suspend fun getUserPhoto(): UserPhotoEntity?
}