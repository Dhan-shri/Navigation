package com.dhanshri.navigation.db



/***
 *  Mvvm is best architecture, MVVM stand for Model, View and ViewModel
 * Model means all thee data management related component, Model has local database related components, remote datasources and repository.
 * The purpose of repository class is to provide clean API for view models to easily get and send data
 * Repository to be mediators bet different data sources
 * ***/

class UsersRepo(private val userDao: UserDao) {

    val users = userDao.getAllSubscribers()

    suspend fun insertUserData(users: Users){
        userDao.insertUsers(users)
    }

    suspend fun updateUserData(users: Users){
        userDao.updateUsers(users)
    }

    suspend fun deleteUserData(users: Users){
        userDao.deleteUsers(users)
    }

    suspend fun deleteAllData(){
        userDao.deleteAllUsers()
    }
}