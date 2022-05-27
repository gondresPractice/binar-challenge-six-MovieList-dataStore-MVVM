package com.binaracademy.movapp_mvvm_datastorage.database.repository

import android.content.Context
import com.binaracademy.movapp_mvvm_datastorage.database.dao_db.user_dao
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.User

class UserRepository(private val userDao: user_dao,private val context: Context) {
    suspend fun registerUser(user:User):Long{
        return userDao.insertUser(user)
    }

    suspend fun loginCheck(username :String,password:String):String{
        return userDao.login(username,password)
    }

    suspend fun getPhotoProfile(username:String):String{
        return userDao.getPhotoProfile(username)
    }

    suspend fun updateProfile(username: String,email:String,images:String,id:Int):Int{
        return userDao.updateUserProfile(id,username,email,images)
    }

    suspend fun getId(username:String) :Int{
        return userDao.getId(username)
    }

    suspend fun getUser(id : Int):User{
        return userDao.getUser(id)
    }
}