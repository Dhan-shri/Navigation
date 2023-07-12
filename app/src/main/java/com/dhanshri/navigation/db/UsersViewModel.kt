package com.dhanshri.navigation.db

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import com.dhanshri.navigation.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class UsersViewModel(private val repo: UsersRepo) : ViewModel() {

    /**
     * For users name and email will define MutableLiveData variable
     * */
    val users = repo.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete: Users


    // to get this data from edittext we used to two way data binding here
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    // using newly created event class as wrapper i.e. type should be event of String
    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
    get() = statusMessage
    /** this status messag is mutable liveData, we can edit its value, we can assign diff value to it.
     * But it is private variable, therfore we can access it from outside classes, that is correct coding practices
    */

    /**
     * Inside init block we should define initial displaying names to those two buttons
     * **/
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun saveOrUpdate(){
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter user's Name")
        }else if (inputEmail.value == null){
            statusMessage.value = Event("Please enter user's Email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Please Enter a correct email address")
        } else {
            if (isUpdateOrDelete){
                userToUpdateOrDelete.name = inputName.value!!
                userToUpdateOrDelete.email = inputEmail.value!!
                updateData(userToUpdateOrDelete)
            }else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insertData(Users(0,name,email))

                inputName.value = ""
                inputEmail.value = ""
            }
        }                         // validation for email

    }

    fun clearAllOrDelete(){
        if (isUpdateOrDelete){
            deleteUserData(userToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun insertData(users: Users){
        viewModelScope.launch(Dispatchers.IO) {
            val newRowId = repo.insertUserData(users)
            withContext(Dispatchers.Main){
                if (newRowId>-1){
                    statusMessage.value = Event("User Inserted Successfully! $newRowId")
                } else{
                    statusMessage.value = Event("Error Occured")
                }
            }
        }
    }

    fun updateData(users: Users) = viewModelScope.launch(Dispatchers.IO) {
        val numOfRows = repo.updateUserData(users)
        withContext(Dispatchers.Main){
            if (numOfRows>0){
                inputName.value = ""
                inputEmail.value = ""

                isUpdateOrDelete = false
                userToUpdateOrDelete = users
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"

                statusMessage.value = Event("$numOfRows updated Successfully")
            } else{
                statusMessage.value = Event("Error Occured")
            }

        }
    }

    fun deleteUserData(users: Users) = viewModelScope.launch (Dispatchers.IO){
        val numOfRowDeleted = repo.deleteUserData(users)
        withContext(Dispatchers.Main){
            if (numOfRowDeleted>0){
                inputName.value = ""
                inputEmail.value = ""

                isUpdateOrDelete = false
                userToUpdateOrDelete = users
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"

                statusMessage.value = Event("$numOfRowDeleted rows Deleted Successfully")
            } else {
                statusMessage.value = Event("Error occurred!")
            }

        }
    }

    fun clearAll() = viewModelScope.launch(Dispatchers.IO){
        val numOfRowsDeleted = repo.deleteAllData()
        withContext(Dispatchers.Main){
            if (numOfRowsDeleted> 0){
                statusMessage.value = Event("$numOfRowsDeleted rows deleted Successfully")
            } else {
                statusMessage.value = Event("Data deleted Successfully")
            }

        }
    }

    // To init the update or delete function on selected user item
    fun initUpdateAndDelete(users: Users){
        /** input name and email are the live data values bind to thosed fields*/
        inputName.value = users.name
        inputEmail.value = users.email

        isUpdateOrDelete = true
        userToUpdateOrDelete = users
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

}