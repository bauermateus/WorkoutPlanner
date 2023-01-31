package com.mbs.workoutplanner.dataBase
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity (
    @PrimaryKey
    val id: Int,

    val title: String,

    val weekDay: String,

    val numberOfExercise: Int,

    val isCompleted: Boolean

)
