package com.binaracademy.movapp_mvvm_datastorage.network

import androidx.lifecycle.MutableLiveData
import com.binaracademy.movapp_mvvm_datastorage.model.MovieDetail
import com.binaracademy.movapp_mvvm_datastorage.model.MovieModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "https://api.themoviedb.org/"
private const val API_KEY = "a87419b6cfe5fd1899b306a97cf55f87"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService{

    @GET("3/movie/634649/recommendations?api_key=$API_KEY&language=en-US&page=1")
    fun allMovies(): Call<MovieModel>

    @GET("3/movie/68726?api_key=$API_KEY&append_to_response=videos")
    fun getDetail(): Call<MovieDetail>
}

object MovieApi{
    private val logging: HttpLoggingInterceptor
    get() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: MovieApiService by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(MovieApiService::class.java)
    }
    val retrofitService:MovieApiService by lazy{retrofit.create(MovieApiService::class.java)}
}