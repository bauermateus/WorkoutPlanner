package com.mbs.workoutplanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _userPhoto: MutableLiveData<ByteArray?> by lazy {
        MutableLiveData<ByteArray?>()
    }
    val userPhoto: LiveData<ByteArray?> get() = _userPhoto

    fun saveInfoToDB(info: UserInfoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUserInfo(info)
        }
    }

    fun savePhotoToDB(photo: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUserPhoto(UserPhotoEntity(photo = photo))
        }
    }

    fun getPhotoFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getUserPhoto()
            withContext(Dispatchers.Main) {
                data?.photo.let {
                    _userPhoto.value = it
                }
            }
        }
    }
}