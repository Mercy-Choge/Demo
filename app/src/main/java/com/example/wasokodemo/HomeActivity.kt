package com.example.wasokodemo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.wasokodemo.room.UserDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity: AppCompatActivity() {
    var btnDelete: Button? = null
    var handler: UserDB? = null
    var context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val buttonedit= findViewById<Button>(R.id.btnEdit)
        buttonedit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
            val buttonview= findViewById<Button>(R.id.btnView)
            buttonview.setOnClickListener {
                val intent = Intent(this, ViewUserActivity::class.java)
                startActivity(intent)
            }
            val buttonadd= findViewById<Button>(R.id.btnAdd)
            buttonadd.setOnClickListener {
                val intent = Intent(this, AddUserActivity::class.java)
                startActivity(intent)
        }
        val buttonlogout = findViewById<Button>(R.id.btnLogout)
        buttonlogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


        val buttondelete = findViewById<Button>(R.id.btnDelete)
        buttondelete.setOnClickListener {
            deleteUserDialog()

        }
    }
    private fun deleteUserDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Delete User")
            setMessage("Do you want to delete user account?!")
            setPositiveButton("ok") { _, _ ->

                val db = Room.databaseBuilder(
                    context,
                    UserDB::class.java, "users.db"
                ).fallbackToDestructiveMigration()
                    .build()
                GlobalScope.launch {
                    db.userDao().deleteUser()
                }

                Toast.makeText(
                    context,
                    " User Deleted...",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(
                    context,
                    MainActivity::class.java
                )
                startActivity(intent)
            }
            setNegativeButton("Decline") { _, _ ->
            }

        }.create().show()
    }
}

