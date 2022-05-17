package com.binaracademy.movapp_mvvm_datastorage.database.model_db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Favorite")
@Parcelize
data class Favorite (
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "subtitle")
    var subtitle: String,
    @ColumnInfo(name = "overview")
    var overview:String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "rating")
    var rating: Double,
    @ColumnInfo(name = "popularity")
    var popularity: String,
) : Parcelable