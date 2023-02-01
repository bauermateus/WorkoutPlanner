package com.mbs.workoutplanner.repository

import com.mbs.workoutplanner.dataBase.WorkoutEntity
import com.mbs.workoutplanner.models.WorkoutModel

interface WorkoutInterface {

    suspend fun fetchWorkouts(): List<WorkoutModel>

    suspend fun insertWorkout(workout: WorkoutEntity)
}