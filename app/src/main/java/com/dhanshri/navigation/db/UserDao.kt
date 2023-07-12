package com.dhanshri.navigation.db

import androidx.lifecycle.LiveData
import androidx.room.*


// To write the queries related to Dao (Data Access Object)
//Room doest support database access on main thread, it might be lock the UI that's why we use suspend function
@Dao
interface UserDao {

    // to insert userd to database
    @Insert
    suspend fun insertUsers(users : Users)  // function in background

    // a suspend function is simply function that can be paused and resumed at later time

    @Update
    suspend fun updateUsers(users: Users)

    @Delete
    suspend fun deleteUsers(users: Users)

    // query to delete all the table
    @Query("DELETE FROM users_data_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users_data_table")
    fun getAllSubscribers():LiveData<List<Users>>

}

