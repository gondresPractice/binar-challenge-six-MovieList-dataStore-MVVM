package com.binaracademy.movapp_mvvm_datastorage.database.repository

import android.content.Context
import com.binaracademy.movapp_mvvm_datastorage.database.dao_db.favorite_dao
import com.binaracademy.movapp_mvvm_datastorage.database.dao_db.user_dao
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.User

class MovieRepository(private val favoriteDao: favorite_dao, private val context: Context) {

    suspend fun insertToFavorite(favorite: Favorite):Long{
        return favoriteDao.insertFavorite(favorite)
    }

    suspend fun getFavorite():List<Favorite>{
        return favoriteDao.getFavorite()
    }

    suspend fun deleteFavorite(movieId : Int):Int{
        return favoriteDao.deleteFavorite(movieId)
    }

    suspend fun getDetailFavorite(movieId: Int):Int {
        return favoriteDao.getDetailFavorite(movieId)
    }
}