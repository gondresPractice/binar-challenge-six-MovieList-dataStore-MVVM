package com.binaracademy.movapp_mvvm_datastorage.pages.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.User
import com.binaracademy.movapp_mvvm_datastorage.database.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterFragmentViewModel(private val repository : UserRepository) : ViewModel(){

    val registervalidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val stringSuccess : MutableLiveData<String> by lazy { MutableLiveData<String>() }


    fun result():LiveData<Boolean>{
        return registervalidation
    }

    fun successValidate():LiveData<String>{
        return stringSuccess
    }

    fun reset(){
        registervalidation.postValue(false)
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.registerUser(user)
            runBlocking(Dispatchers.Main){
                if (result!=0 .toLong()){
                    registervalidation.postValue(true)
                    stringSuccess.postValue("Succcess");
                }
            }
        }
    }
}