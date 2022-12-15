package com.mbs.workoutplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {








    class Factory(private val repository: WorkoutRepository) :ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WorkoutViewModel(repository) as T
        }
    }
}