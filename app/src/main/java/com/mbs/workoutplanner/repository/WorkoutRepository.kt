package com.mbs.workoutplanner.repository

import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.models.WorkoutModel

class WorkoutRepository(appDataBase: AppDataBase) : WorkoutInterface {

    private val dao = appDataBase.workoutDao()

    override suspend fun fetchWorkouts(): List<WorkoutModel> {
        return dao.fetchAllWorkouts().map {
            WorkoutModel(
                id = it.id,
                title = it.title,
                weekDay = it.weekDay,
                numberOfExercise = it.numberOfExercise,
                isCompleted = it.isCompleted
            )
        }
    }
}