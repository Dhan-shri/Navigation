package com.dhanshri.navigation.db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dhanshri.navigation.R
import com.dhanshri.navigation.databinding.ActivityRoomBinding

class RoomActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityRoomBinding
    private lateinit var viewModel: UsersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)

        val dao = UsersDatabase.getInstance(applicationContext).userDao
        val repo = UsersRepo(dao)
        val factory = UsersViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory)[UsersViewModel::class.java]
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this

        displayUsersList()

        binding.btnSave.setOnClickListener {
            viewModel.saveOrUpdate()
        }

        binding.btnClear.setOnClickListener {
            viewModel.clearAllOrDelete()
        }
    }
    /**
     * Creating function to observe the list of users data in the Database
     */

    private fun displayUsersList(){
        // Write codes here to observe the list of users, which is in live data format
        viewModel.users.observe(this) {
            Log.i("MYTAG", it.toString())
        }

    }
}