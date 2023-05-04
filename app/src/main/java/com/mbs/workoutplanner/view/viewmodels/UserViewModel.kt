package com.mbs.workoutplanner.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplanner.models.UserDataModel
import com.mbs.workoutplanner.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserDataRepository) : ViewModel() {

    private val _userData by lazy {
        MutableLiveData<UserDataModel>()
    }
    val userData: LiveData<UserDataModel> get() = _userData

    fun saveUser(userDataModel: UserDataModel) {
        viewModelScope.launch {
            if (userDataModel.bf == null) {
                val user = userDataModel.toMap().toMutableMap()
                user["bf"] = userData.value?.bf
                repository.registerUserData(
                    UserDataModel(
                        user["name"] as String,
                        user["modality"] as String,
                        user["weight"] as Double,
                        user["height"] as Long,
                        user["bf"] as Double
                    )
                )
            } else {
                repository.registerUserData(userDataModel)
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch(Dispatchers.Main) {
            _userData.value = repository.retrieveUserData()
        }
    }


}