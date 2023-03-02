package com.mbs.workoutplanner.repository

import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.dataBase.WorkoutDao
import com.mbs.workoutplanner.dataBase.WorkoutEntity
import com.mbs.workoutplanner.models.WorkoutModel
import javax.inject.Inject

class WorkoutRepository @Inject constructor(private val dao: WorkoutDao) : WorkoutInterface {

    override suspend fun fetchWorkouts(): List<WorkoutModel> {
        return dao.fetchAllWorkouts().map {
            WorkoutModel(
                id = it.id,
                title = it.title,
                weekDay = it.weekDay,
                numberOfExercise = it.numberOfExercise
            )
        }
    }

    override suspend fun insertWorkout(workout: WorkoutEntity) {
        dao.insertWorkout(workout)
    }
}