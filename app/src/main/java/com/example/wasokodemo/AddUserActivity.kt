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

class AddUserActivity: AppCompatActivity() {
    var btncreate: Button? = null
    var userfirstname: EditText? = null
    var userlastname: EditText? = null
    var useremail: EditText? = null
    var usernamec: EditText? = null
    var userpassword: EditText? = null
    var passwordconfirm: EditText? = null
    var context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adduser)
        btncreate = findViewById(R.id.btncreateuser)
        userfirstname = findViewById(R.id.userfname)
        userlastname = findViewById(R.id.userlname)
        useremail = findViewById(R.id.useremail)
        usernamec = findViewById(R.id.createusername)
        userpassword = findViewById(R.id.userpassw)
        passwordconfirm = findViewById(R.id.passwdconfirm)
        val btncreate = findViewById<Button>(R.id.btncreateuser)
        btncreate.setOnClickListener {
            try {
                val firstName = userfirstname?.text.toString()
                val lastName = userlastname?.text.toString()
                val Uemail = useremail?.text.toString()
                val userName = usernamec?.text.toString()
                val Upassword = userpassword?.text.toString()
                if (firstName.trim { it <= ' ' } == "") {
                    userfirstname!!.error = "Please enter user firstname"
                } else if (lastName.trim { it <= ' ' } == "") {
                    userlastname!!.error = "Please enter user password"
                } else if (Uemail.trim { it <= ' ' } == "") {
                    useremail!!.error = "Please enter user email"
                } else if (userName.trim { it <= ' ' } == "") {
                    usernamec!!.error = "Please enter username"
                } else if (Upassword.trim { it <= ' ' } == "") {
                    userpassword!!.error = "Please enter user password"
                } else {
                    val db = Room.databaseBuilder(
                        context,
                        UserDB::class.java, "users.db"
                    ).fallbackToDestructiveMigration()
                        .build()


                    GlobalScope.launch {


                        val users = Users(firstName, lastName, Uemail, userName, Upassword)

                        GlobalScope.launch {
                            db.userDao().insertUser(users)
                        }
                        Toast.makeText(
                            context,
                            "User Added Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(
                            context,
                            ViewUserActivity::class.java
                        )
                        startActivity(intent)
                    }
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
