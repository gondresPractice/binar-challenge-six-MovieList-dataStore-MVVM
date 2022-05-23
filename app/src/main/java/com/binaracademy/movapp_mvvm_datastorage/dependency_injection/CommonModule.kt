package com.binaracademy.movapp_mvvm_datastorage.dependency_injection

import androidx.room.Room
import com.binaracademy.movapp_mvvm_datastorage.database.create_db.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.dsl.single


val localModule = module{
    factory{get<UserDatabase>().UserDao()}
    factory{get<UserDatabase>().FavoriteDao()}
    single{
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "MovieApp.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}