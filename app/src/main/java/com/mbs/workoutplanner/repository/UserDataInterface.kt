package com.mbs.workoutplanner.repository

import com.google.firebase.firestore.DocumentReference
import com.mbs.workoutplanner.models.UserDataModel

interface UserDataInterface {

    suspend fun registerUserData(user: UserDataModel)

    suspend fun  retrieveUserData() : UserDataModel?
}