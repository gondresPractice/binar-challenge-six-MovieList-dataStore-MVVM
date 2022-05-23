package com.binaracademy.movapp_mvvm_datastorage.pages.login

import android.util.Log
import androidx.lifecycle.*
import com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager
import com.binaracademy.movapp_mvvm_datastorage.database.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository:UserRepository,private val dataStoreManager : UserManager): ViewModel() {

    val loginPref:MutableLiveData<Boolean> by lazy{MutableLiveData<Boolean>()}

    fun loginPrefResult(): LiveData<Boolean> {
        return loginPref
    }
    fun loginCheck(username:String,password:String){
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.loginCheck(username,password)
            runBlocking(Dispatchers.Main){
                if(result!=null){
                    loginPref.postValue(true)
                    viewModelScope.launch {
                        Log.d("BANGKEE",username)
                        dataStoreManager.storePrefs(username, true)
                        dataStoreManager.userNameFlow.asLiveData()
                    }
                    Log.d("Login","Success")
                    }
                else{
                    loginPref.postValue(true)
                    viewModelScope.launch {
                        Log.d("BANGKEE",username)
                        dataStoreManager.storePrefs(username, true)
                        dataStoreManager.userNameFlow.asLiveData()
                    }
                    Log.d("Login","Failed")
                }
            }
        }
    }
}