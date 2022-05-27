package com.binaracademy.movapp_mvvm_datastorage.pages.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite
import com.binaracademy.movapp_mvvm_datastorage.database.repository.MovieRepository
import com.binaracademy.movapp_mvvm_datastorage.database.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(
    private val repository: MovieRepository,
    private val dataStoreManager: UserManager
) :
    ViewModel() {
        val checkInsert : MutableLiveData<Boolean> by lazy {MutableLiveData<Boolean>()}
        val checkFavorite : MutableLiveData<Boolean> by lazy {MutableLiveData<Boolean>()}
        val deleteFavorite : MutableLiveData<Boolean> by lazy {MutableLiveData<Boolean>()}


    fun checkStatInsert():LiveData<Boolean>{
        return checkInsert
    }

    fun checkStatFavorite():LiveData<Boolean>{
        return checkFavorite
    }

    fun checkStatDelete():LiveData<Boolean>{
        return deleteFavorite
    }

        fun insertToFavorite(favorite: Favorite){
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.insertToFavorite(favorite)
                runBlocking (Dispatchers.Main){
                    if (result!=null){
                        Log.d("Insert Favorite","True")
                        checkInsert.postValue(true)
                    }else{

                        Log.d("Insert Favorite","False")
                        checkInsert.postValue(false)
                    }
                }
            }
        }

    fun checkFavoriteData(movieId : Int){
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.getDetailFavorite(movieId)
            runBlocking(Dispatchers.Main){
                if (result!=null){
                    Log.d("Check Favorite Data","True")
                    checkFavorite.postValue(true)
                }else{
                    Log.d("Check Favorite Data","")
                    checkFavorite.postValue(false)
                }
            }
        }
    }

    fun deleteFromFavorite(movieId: Int){
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.deleteFavorite(movieId)
            runBlocking(Dispatchers.Main) {
                if (result!=null){
                    deleteFavorite.postValue(true)
                }else{
                    deleteFavorite.postValue(false)
                }
            }
        }
    }
}