package com.binaracademy.movapp_mvvm_datastorage.database.model_db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "User")
@Parcelize
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "images")
    var images:String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "repassword")
    var repassword: String,
) : Parcelable