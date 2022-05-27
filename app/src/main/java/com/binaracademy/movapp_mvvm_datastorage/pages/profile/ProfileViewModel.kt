package com.binaracademy.movapp_mvvm_datastorage.pages.profile

import android.util.Log
import androidx.datastore.DataStore
import androidx.lifecycle.*
import com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.User
import com.binaracademy.movapp_mvvm_datastorage.database.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val repository: UserRepository,private val dataStore : UserManager): ViewModel() {
    val idProfile : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val updateSuccess : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val id : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val username : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val email : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val password : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val rePassword : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val images : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val users : MutableLiveData<User> by lazy { MutableLiveData<User>() }

    fun getUsername(): LiveData<String> {
        return dataStore.userNameFlow.asLiveData()
    }

    fun getUsers(user:User): LiveData<User>{
        return users
    }

    fun userName():LiveData<String>{
        return username
    }
    fun getImages():LiveData<String>{
        return images
    }
    fun getEmail():LiveData<String>{
        return email
    }

    fun getId():LiveData<Int>{
        return id
    }
    fun getIdProfile(usernameProfile:String){
        Log.d("Test Id",usernameProfile)
        viewModelScope.launch(Dispatchers.IO) {
          val resultId = repository.getId(usernameProfile)
            Log.d("Test ResultId",resultId.toString())
            runBlocking(Dispatchers.Main) {
                if(resultId!=0){
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = repository.getUser(resultId)
                        runBlocking(Dispatchers.Main) {
                            if(result!=null){
                                email.postValue(result.email)
                                username.postValue(result.username)
                                images.postValue(result.images)
                                id.postValue(result.id)
                                var resultUser = User(
                                    result.id,
                                    result.username,
                                    result.email,
                                    result.images,
                                    result.password,
                                    result.repassword
                                )
                                users.postValue(resultUser)
                            }else{
                                Log.d("Get Id Profile","Failed")
                            }
                        }
                    }
                }else{

                }
            }
            Log.d("Get ResultId Profile","Failed")
        }
    }

    fun updateUser(idProfile:Int,username:String,email:String,images:String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateProfile(username,email,images,idProfile)
            runBlocking(Dispatchers.Main) {
                if (result!=null){
                    Log.d("Update User","Success")
                }else{

                    Log.d("Update User","Failed")
                }
            }
        }
    }
}