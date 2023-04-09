package com.mbs.workoutplanner.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mbs.workoutplanner.dataBase.UserInfoEntity
import com.mbs.workoutplanner.dataBase.UserPhotoEntity
import com.mbs.workoutplanner.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserInfoRepository) : ViewModel() {

    private val _userData by lazy {
        MutableLiveData<FirebaseUser>()
    }
    val userData: LiveData<FirebaseUser> get() = _userData

    fun saveUser(user: FirebaseUser) {
        _userData.value = user
    }


}