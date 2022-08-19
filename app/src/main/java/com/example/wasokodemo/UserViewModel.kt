package com.example.wasokodemo

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import com.example.wasokodemo.room.UserDB
import com.example.wasokodemo.room.UserDao
import com.example.wasokodemo.room.Users


class UserViewModel(app: Application): AndroidViewModel(app) {

    lateinit var allUsers: MutableLiveData<List<Users>>



    init {
        allUsers = MutableLiveData()
    }

    fun getAllUsersObservers():MutableLiveData<List<Users>>{
        return allUsers
    }


    fun getUserListing(){
       val userDao = UserDB.getAppDatabase((getApplication())).userDao()
        val list = userDao.getAll()

        allUsers.postValue(list)
    }


}