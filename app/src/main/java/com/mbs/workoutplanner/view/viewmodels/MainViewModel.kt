package com.mbs.workoutplanner.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplanner.models.WorkoutModel
import com.mbs.workoutplanner.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _workoutsList = MutableLiveData<List<WorkoutModel>>()

    val workoutsList: LiveData<List<WorkoutModel>> get() = _workoutsList

    fun getAllWorkouts() {

    }
    fun insertWorkout() {

    }
}