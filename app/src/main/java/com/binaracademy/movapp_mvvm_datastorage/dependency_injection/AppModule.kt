package com.binaracademy.movapp_mvvm_datastorage.dependency_injection

import com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager
import com.binaracademy.movapp_mvvm_datastorage.database.repository.MovieRepository
import com.binaracademy.movapp_mvvm_datastorage.database.repository.UserRepository
import com.binaracademy.movapp_mvvm_datastorage.pages.detail.DetailViewModel
import com.binaracademy.movapp_mvvm_datastorage.pages.favorite.FavoriteViewModel
import com.binaracademy.movapp_mvvm_datastorage.pages.home.HomeViewModel
import com.binaracademy.movapp_mvvm_datastorage.pages.login.LoginViewModel
import com.binaracademy.movapp_mvvm_datastorage.pages.register.RegisterFragmentViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@DelicateCoroutinesApi
val viewModelModule = module{
    viewModel {RegisterFragmentViewModel(get())}
    viewModel {LoginViewModel(get(),get())}
    viewModel {HomeViewModel(get(),get())}
    viewModel {DetailViewModel(get(),get())}
    viewModel {FavoriteViewModel(get())}
}
val repositoryModule = module{
    single{ UserRepository(get(), androidContext()) }
    single {MovieRepository(get(),androidContext())}
}

val dataStoreModule = module{
    single{UserManager(androidContext())}
}