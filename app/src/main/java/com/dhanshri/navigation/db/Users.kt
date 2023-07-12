package com.dhanshri.navigation.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "users_data_table")
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    var name : String,
    var email : String
)