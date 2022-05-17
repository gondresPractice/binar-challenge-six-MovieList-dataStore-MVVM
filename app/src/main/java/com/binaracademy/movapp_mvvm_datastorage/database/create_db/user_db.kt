package com.binaracademy.movapp_mvvm_datastorage.database.create_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binaracademy.movapp_mvvm_datastorage.database.dao_db.favorite_dao
import com.binaracademy.movapp_mvvm_datastorage.database.dao_db.user_dao
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.User

@Database(entities = [User::class, Favorite::class], version = 4)
abstract class UserDatabase : RoomDatabase() {

    abstract fun UserDao(): user_dao
    abstract fun FavoriteDao() : favorite_dao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(User::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "User.db*"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}