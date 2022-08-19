package com.example.wasokodemo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users(
    @PrimaryKey()

    @ColumnInfo(name = "first_name")
    val firstname: String,

    @ColumnInfo(name = "last_name")
    val lastname: String,

    @ColumnInfo(name = "user_email")
    val email: String,

    @ColumnInfo(name = "user_name")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String,
)
