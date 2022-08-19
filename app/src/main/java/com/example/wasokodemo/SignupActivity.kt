package com.example.wasokodemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.wasokodemo.room.UserDB
import com.example.wasokodemo.room.Users
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupActivity: AppCompatActivity() {
    var btnsign: Button? = null
    var firstname: EditText? = null
    var lastname: EditText? = null
    var email: EditText? = null
    var username: EditText? = null
    var password: EditText? = null
    var handler: UserDB? = null
    var context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiivity_signup)


        btnsign = findViewById(R.id.btnsignup)
        firstname = findViewById(R.id.fname)
        lastname= findViewById(R.id.lname)
        email = findViewById(R.id.email)
        username = findViewById(R.id.usern)
        password = findViewById(R.id.passw)
        handler = UserDB(this)
        val btnsign = findViewById<Button>(R.id.btnsignup)
        btnsign.setOnClickListener {
            try {
                val firstName = firstname?.text.toString()
                val lastName = lastname?.text.toString()
                val Uemail = email?.text.toString()
                val userName = username?.text.toString()
                val Upassword = password?.text.toString()
                if (firstName.trim { it <= ' ' } == "") {
                    firstname!!.error = "Please enter firstname"
                } else if (lastName.trim { it <= ' ' } == "") {
                    lastname!!.error = "Please enter password"
                } else if (Uemail.trim { it <= ' ' } == "") {
                    email!!.error = "Please enter email"
                } else if (userName.trim { it <= ' ' } == "") {
                    username!!.error = "Please enter username"
                } else if (Upassword.trim { it <= ' ' } == "") {
                    password!!.error = "Please enter password"
                } else {
                    val db = Room.databaseBuilder(
                        context,
                        UserDB::class.java, "users.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    val users = Users(firstName,lastName, Uemail, userName, Upassword)

                    GlobalScope.launch {
                        db.userDao().insertUser(users)
                    }
                    Toast.makeText(
                        context,
                        "User Added Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    Toast.makeText(
                        context,
                        "Back to Login Activity",
                        Toast.LENGTH_LONG
                    ).show()

                   /* val userList = db.userDao().getUserByCredentials(userName.trim(), Uemail.trim())
                    if (userList.equals(users)) {
                        runOnUiThread {
                            Toast.makeText(
                                context,
                                "User already exists",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        return@setOnClickListener
                    }*/

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
