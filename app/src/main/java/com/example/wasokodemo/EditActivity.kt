package com.example.wasokodemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.wasokodemo.room.UserDB
import com.example.wasokodemo.room.Users
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val context = this
        val emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val firstnameed = findViewById<EditText>(R.id.editfirstname)
        val lastnameed = findViewById<EditText>(R.id.editlastname)
        val emailed = findViewById<EditText>(R.id.editemail)
        val usernameed = findViewById<EditText>(R.id.editusername)
        val passworded = findViewById<EditText>(R.id.editpassword)
        val buttonsearch = findViewById<Button>(R.id.btnsearch)
        val etuserparam = findViewById<EditText>(R.id.edusername)
        buttonsearch.setOnClickListener {
            try {

                val userName = etuserparam.text.toString()


                val db = Room.databaseBuilder(
                    context,
                    UserDB::class.java, "users.db"
                ).fallbackToDestructiveMigration()
                    .build()


                GlobalScope.launch {

                    val allUsers = db.userDao().getAllUsers()
                    if (allUsers.isNotEmpty()) {
                        val userList = db.userDao().getUsersByUserName(userName)
                        if (userList.isNotEmpty()) {
                            runOnUiThread {
                                val user = userList.first()
                                firstnameed.setText(user.firstname)
                                lastnameed.setText(user.lastname)
                                emailed.setText(user.email)
                                usernameed.setText(user.username)
                                passworded.setText(user.password)
                            }

                        }else{
                            runOnUiThread { Toast.makeText(
                                context,
                                "User not available",
                                Toast.LENGTH_LONG
                            ).show()}


                        }
                    } else {
                        runOnUiThread{ Toast.makeText(
                            context,
                            "You don't have any users saved at the moment",
                            Toast.LENGTH_LONG
                        ).show()}

                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        val btnupdate = findViewById<Button>(R.id.btnupdate)
        btnupdate.setOnClickListener {
            try {
                val firstName = firstnameed?.text.toString()
                val lastName = lastnameed?.text.toString()
                val Uemail = emailed?.text.toString()
                val userName = usernameed?.text.toString()
                val Upassword = passworded?.text.toString()


                if (!Uemail.matches(emailPattern.toRegex()))
                {
                    emailed!!.error="Please enter a valid email"
                }
                else if (firstName.trim { it <= ' ' } == "") {
                    firstnameed!!.error = "new firstname not entered"
                } else if (lastName.trim { it <= ' ' } == "") {
                    lastnameed!!.error = "new password not entered"
                } else if (Uemail.trim { it <= ' ' } == "") {
                    emailed!!.error = "new email not entered"
                } else if (userName.trim { it <= ' ' } == "") {
                    usernameed!!.error = "new username  not entered"
                } else if (Upassword.trim { it <= ' ' } == "") {
                    passworded!!.error = "new password not entered"
                } else {
                    val db = Room.databaseBuilder(
                        context,
                        UserDB::class.java, "users.db"
                    ).fallbackToDestructiveMigration()
                        .build()

                    val users = Users(firstName, lastName, Uemail,userName, Upassword)
                    GlobalScope.launch {
                        db.userDao().updateUser(users)
                    }
                    Toast.makeText(
                        context,
                        "User Updated",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(
                        context,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Toast.makeText(
                    context,
                    ex.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}