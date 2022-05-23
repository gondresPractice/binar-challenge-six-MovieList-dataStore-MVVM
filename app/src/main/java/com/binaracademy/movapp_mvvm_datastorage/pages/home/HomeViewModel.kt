package com.binaracademy.movapp_mvvm_datastorage.pages.home

import android.util.Log
import androidx.lifecycle.*
import com.binaracademy.movapp_mvvm_datastorage.database.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager

class HomeViewModel(private val repository: UserRepository,private val userManager : UserManager) : ViewModel() {

    val userName : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val photoResult : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun getUsername():LiveData<String>{
        return userManager.userNameFlow.asLiveData()
    }

    fun getPhoto():LiveData<String>{
        return photoResult
    }
    fun getPhotoProfile(username:String){

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getPhotoProfile(username)
            runBlocking(Dispatchers.Main) {
                if (result!=null){
                    photoResult.postValue(result)
                    userName.postValue(username)
                }else{
                    Log.d("Failed","Failed!!")
                }
            }
        }
    }
}