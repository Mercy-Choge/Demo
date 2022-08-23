package com.example.wasokodemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wasokodemo.room.UserDB

class ViewUserMain: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_row)

        val buttonedituser = findViewById<Button>(R.id.btnEditUserDetails)
        buttonedituser.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)

        }
        val database = Room.databaseBuilder(
            this, UserDB::class.java, "user_listing"
        )
            .allowMainThreadQueries()
            .build()

        val allUsers = database.userDao().getAllUsers()
        val usersRecyclerView =  findViewById<RecyclerView>(R.id.recyclerview)
        usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ViewUserMain)
            adapter = UserAdapter(allUsers)

        }


        val viewModel = ViewModelProvider.of (this).get(ViewUserMain::class.java)
        viewModel.getAllUsersObservers().observe(this, Observer {
            val recyclerViewAdapter = UserAdapter(allUsers)
            recyclerViewAdapter.setListData(arrayListOf(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })




    }

}