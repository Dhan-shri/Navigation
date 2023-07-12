package com.dhanshri.navigation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Represent the actual database of the app, create abstract class, which is subclass of RoomDatabase class

// version number is important when we are migrating the database from one version to another, we have more than one enities class
@Database(entities = [Users::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract val userDao : UserDao

    //create a new object and use this database class in other places of the project but, usually we should only use one instance of room database for the entire app.
    // TO avoid unexpected error

    //companion objects are singleton objects whose properties and functions are tied to a class but not to the instance of that class
    companion object{
        @Volatile
        private var INSTANCE : UsersDatabase? = null
            fun getInstance(context : Context) : UsersDatabase{
                synchronized(this){
                    var instance = INSTANCE
                    if (instance == null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            UsersDatabase::class.java,
                            "users_data_table"
                        ).build()
                        INSTANCE = instance
                    }
                    return instance
                }
            }
    }
}

