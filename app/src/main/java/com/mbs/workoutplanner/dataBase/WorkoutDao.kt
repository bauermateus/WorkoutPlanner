package com.mbs.workoutplanner.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mbs.workoutplanner.models.WorkoutModel
@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts")
    suspend fun fetchAllWorkouts(): List<WorkoutEntity>

    @Insert
    suspend fun insertWorkout(workout: WorkoutEntity)
}