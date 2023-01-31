package com.mbs.workoutplanner.repository

import com.mbs.workoutplanner.models.WorkoutModel

interface WorkoutInterface {

    suspend fun fetchWorkouts(): List<WorkoutModel>
}