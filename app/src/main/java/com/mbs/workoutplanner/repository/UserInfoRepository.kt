package com.mbs.workoutplanner.repository

import com.mbs.workoutplanner.dataBase.UserDao
import com.mbs.workoutplanner.dataBase.UserInfoEntity
import com.mbs.workoutplanner.dataBase.UserPhotoEntity
import javax.inject.Inject

class UserInfoRepository @Inject constructor(private val dao: UserDao): UserInfoInterface {
    override suspend fun insertUserInfo(info: UserInfoEntity) {
        dao.insertUserInfo(info)
    }

    override suspend fun insertUserPhoto(photo: UserPhotoEntity) {
        dao.insertUserPhoto(photo)
    }

    override suspend fun getUserPhoto(): UserPhotoEntity? {
        return dao.getUserPhoto()
    }

}