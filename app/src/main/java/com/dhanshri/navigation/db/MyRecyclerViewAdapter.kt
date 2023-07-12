package com.dhanshri.navigation.db

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBinderMapperImpl
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dhanshri.navigation.R
import com.dhanshri.navigation.databinding.UserListItemBinding


/***
 * Recyclerview's class whenever we are extending, we need to provide an adapter class as the object type(viewholder class). You could create this as an inner class or a separate clas in the same file
 */

class MyRecyclerViewAdapter( private val clickListener: (Users)->Unit) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val usersList =  ArrayList<Users>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // here we create the list Item
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: UserListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.user_list_item, parent, false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        // This function return the total number of items in the data set held by the adapter
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*** We use this function to display data on the list item. Position represent the count value of each list item*/

        holder.bind(usersList[position], clickListener)

    }

    // function to set the list of users to this adapter

    fun setList(users: List<Users>){
        usersList.clear()
        usersList.addAll(users)
    }


}



class MyViewHolder(val binding: UserListItemBinding):RecyclerView.ViewHolder(binding.root){
    // Mainly use this class to bind values to each List Item

    fun bind(users: Users, clickListener: (Users)->Unit){
        binding.nameTextView.text = users.name
        binding.emailTextView.text = users.email
        binding.listItemLayout.setOnClickListener {
            clickListener(users)
        }
    }
}