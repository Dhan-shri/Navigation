package com.dhanshri.navigation.db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanshri.navigation.R
import com.dhanshri.navigation.databinding.ActivityRoomBinding

class RoomActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityRoomBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)

        val dao = UsersDatabase.getInstance(applicationContext).userDao
        val repo = UsersRepo(dao)
        val factory = UsersViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory)[UsersViewModel::class.java]
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        // Write code to observe liveData

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

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
//            binding.usersRecyclerview.adapter = MyRecyclerViewAdapter(it,{selectedItem:Users -> listItemClicked(selectedItem)})
            adapter.setList(it)
            adapter.notifyDataSetChanged() // will update adapterView that there is new update

            // selectedItem:Users -> listItemClicked(selectedItem) this is for the click event on item will change in adapter class as well
        }

    }

    private fun initRecyclerView (){
        binding.usersRecyclerview.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem:Users -> listItemClicked(selectedItem)})

        displayUsersList()
    }

    // list item click
    private fun listItemClicked(users: Users){
        Toast.makeText(this, "Selected name is ${users.name}", Toast.LENGTH_LONG).show()
        // to implement click event we use kotlin higher order function and lambda expression
        viewModel.initUpdateAndDelete(users)
    }
}