package com.dhanshri.navigation.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsersViewModelFactory(private val repo: UsersRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}