package com.dhanshri.navigation.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(private val repo: UsersRepo) : ViewModel() {

    /**
     * For users name and email will define MutableLiveData variable
     * */
    val users = repo.users

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    /**
     * Inside init block we should define initial displaying names to those two buttons
     * **/
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun saveOrUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insertData(Users(0,name,email))

        inputName.value = ""
        inputEmail.value = ""
    }

    fun clearAllOrDelete(){
        clearAll()
    }

    fun insertData(users: Users){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertUserData(users)
        }
    }

    fun updateData(users: Users) = viewModelScope.launch(Dispatchers.IO) {
        repo.updateUserData(users)
    }

    fun deleteUserData(users: Users) = viewModelScope.launch (Dispatchers.IO){
        repo.deleteUserData(users)
    }

    fun clearAll() = viewModelScope.launch(Dispatchers.IO){
        repo.deleteAllData()
    }


}