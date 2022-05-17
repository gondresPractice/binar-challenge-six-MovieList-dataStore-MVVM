package com.binaracademy.movapp_mvvm_datastorage.database.dao_db

import androidx.room.*
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite

@Dao
interface favorite_dao {


    @Query("SELECT * From Favorite ")
    fun getFavorite(): List<Favorite>

    @Query("SELECT * From Favorite Where id=:id ")
    fun getDetailFavorite(id:Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite : Favorite) :Long

    @Query ("DELETE From Favorite Where id =:movieId")
    fun deleteFavorite(movieId:Int): Int
}