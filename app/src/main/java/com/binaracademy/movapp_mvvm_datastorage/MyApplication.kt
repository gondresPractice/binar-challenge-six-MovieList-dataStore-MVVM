package com.binaracademy.movapp_mvvm_datastorage

import android.app.Application
import com.binaracademy.movapp_mvvm_datastorage.dependency_injection.dataStoreModule
import com.binaracademy.movapp_mvvm_datastorage.dependency_injection.localModule
import com.binaracademy.movapp_mvvm_datastorage.dependency_injection.repositoryModule
import com.binaracademy.movapp_mvvm_datastorage.dependency_injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
         modules(
                listOf(
                    localModule,
                    viewModelModule,
                    repositoryModule,
                    dataStoreModule
                )
            )
        }
    }
}