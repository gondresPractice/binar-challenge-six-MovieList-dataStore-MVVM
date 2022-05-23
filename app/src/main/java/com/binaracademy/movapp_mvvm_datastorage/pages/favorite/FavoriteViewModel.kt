package com.binaracademy.movapp_mvvm_datastorage.pages.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite
import com.binaracademy.movapp_mvvm_datastorage.database.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository : MovieRepository): ViewModel() {

    val favoriteList : MutableLiveData<List<Favorite>> by lazy{ MutableLiveData <List<Favorite>>()}

    fun getFavorite():LiveData<List<Favorite>>{
        return favoriteList
    }

    fun getFavoriteList(){
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.getFavorite()
            favoriteList.postValue(result)
        }
    }
}