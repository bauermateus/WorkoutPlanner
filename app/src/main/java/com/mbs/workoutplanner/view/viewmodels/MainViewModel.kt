package com.mbs.workoutplanner.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplanner.dataBase.WorkoutEntity
import com.mbs.workoutplanner.models.WorkoutModel
import com.mbs.workoutplanner.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WorkoutRepository) : ViewModel() {

    private val _workoutsList = MutableLiveData<List<WorkoutModel>>()

    val workoutsList: LiveData<List<WorkoutModel>> get() = _workoutsList

    fun getAllWorkouts() {
        viewModelScope.launch(Dispatchers.IO) {
            val workouts = repository.fetchWorkouts()
            withContext(Dispatchers.Main) {
                 _workoutsList.value = workouts
            }
        }
    }
    fun insertWorkout(workout: WorkoutEntity) {
        viewModelScope.launch(Dispatchers.IO) {
        repository.insertWorkout(workout)
        }
    }
}