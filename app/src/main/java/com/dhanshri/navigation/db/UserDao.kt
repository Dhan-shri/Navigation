package com.dhanshri.navigation.db

import androidx.lifecycle.LiveData
import androidx.room.*


// To write the queries related to Dao (Data Access Object)
//Room doest support database access on main thread, it might be lock the UI that's why we use suspend function



@Dao
interface UserDao {

    // to insert userd to database
    @Insert
    suspend fun insertUsers(users : Users) : Long //1
    // function in background
    // a suspend function is simply function that can be paused and resumed at later time

    @Update
    suspend fun updateUsers(users: Users) : Int

    @Delete
    suspend fun deleteUsers(users: Users) : Int

    // query to delete all the table
    @Query("DELETE FROM users_data_table")
    suspend fun deleteAllUsers() : Int

    @Query("SELECT * FROM users_data_table")
    fun getAllSubscribers():LiveData<List<Users>>

}


/***
 * Return values of room for proper, much accurate verification:-
 * 1. a function annoted with @Insert can return long value, which is new rowID for the inserted item ( It if not an arry or collection, it should return an array of Longs Values or list of Long
 * 2. @Update from Int
 * */