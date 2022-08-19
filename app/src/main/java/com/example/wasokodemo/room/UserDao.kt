package com.example.wasokodemo.room

import androidx.room.*

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM users")
    fun getAll(): List<Users>

    @Transaction
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Users


    @Transaction
    @Query("SELECT * FROM users WHERE user_name LIKE :userName AND " +
            "user_email LIKE :Uemail LIMIT 1")
    fun getUserByCredentials(userName: String, Uemail: String): List<Users>

    @Transaction
    @Query("SELECT * FROM users WHERE first_name LIKE :firstName AND " + "last_name LIKE :lastName AND " +"user_email LIKE :Uemail LIMIT 1")
    fun getUserListing(firstName: String,lastName:String, Uemail: String): List<Users>

    @Transaction
    @Query("SELECT * FROM users WHERE password LIKE :password LIMIT 1")
    fun getUserByPassword(password: String): List<Users>


    @Transaction
    @Query("SELECT * FROM users WHERE user_name LIKE :userName AND " +
            "password LIKE :password LIMIT 1")
    fun authenticateUsers(userName: String, password: String): List<Users>


    @Transaction
    @Query("SELECT * FROM users")
    fun getAllUsers():List<Users>

    @Transaction
    @Query("SELECT * FROM users WHERE user_name  =:userName LIMIT 1 ")
    fun getUsersByUserName(userName: String):List<Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: Users): Long

    @Update
    fun updateUser(users: Users): Int

    @Query("DELETE FROM users")
    fun deleteUser ()
}