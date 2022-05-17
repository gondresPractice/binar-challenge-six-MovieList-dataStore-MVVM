package com.binaracademy.movapp_mvvm_datastorage.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovieModel (
    val id : Int,
    val title :String,
    val subtitle : String,
    val overview : String,
    val date :String,
    val image:String,
    val rating : Double,
    val popularity : String
        ): Parcelable