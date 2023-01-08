package com.mbs.workoutplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel(private val repository: WorkoutRepository) : ViewModel() {

    //viewmodel


    class Factory(private val repository: WorkoutRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}