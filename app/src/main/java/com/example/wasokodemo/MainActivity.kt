package com.example.wasokodemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.wasokodemo.room.UserDB
import com.example.wasokodemo.room.Users
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ViewUserActivity.RowClickListener {
    var btnLogin: Button? = null
    var btnsign: Button? = null
    var username: EditText? = null
    var password: EditText? = null
    var handler: UserDB? = null
    private val context = this


    lateinit var recyclerViewAdapter: ViewUserActivity
    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



       /*recyclerView.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter

        }*/
       /* viewModel = ViewModelProvider.of(this).get(MainActivityViewModel::class.java)*/
        viewModel.getAllUsersObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })

        handler = UserDB(this)
        username = findViewById(R.id.username)
        password = findViewById(R.id.Password)
        btnLogin = findViewById(R.id.firstlog)
        btnsign = findViewById(R.id.btnsignup)



        val btnsign = findViewById<Button>(R.id.btnac)
        btnsign.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        val btnLogin = findViewById<Button>(R.id.firstlog)
        btnLogin.setOnClickListener {


            val userName = username?.getText().toString()
            val Upassword = password?.getText().toString()

            when {
                userName.trim { it <= ' ' } == "" -> {
                    username!!.error = "Please enter username"
                }
                Upassword.trim { it <= ' ' } == "" -> {
                    password!!.error = "Please enter password"
                }
                else -> {
                    val db = Room.databaseBuilder(
                        context,
                        UserDB::class.java, "users.db"
                    ).fallbackToDestructiveMigration()
                        .build()

                    GlobalScope.launch {
                        val userList = db.userDao().getUsersByUserName(userName.trim())
                        if (userList.isEmpty()) {
                            runOnUiThread {
                                Toast.makeText(
                                    context,
                                    "User not available",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            return@launch
                        }
                        val usersList = db.userDao().getUserByPassword(Upassword.trim())
                        if (usersList.isEmpty()) {
                            runOnUiThread {
                                Toast.makeText(
                                    context,
                                    "Wrong password",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }else{
                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        }
                    }


                }
            }
        }

    override fun onEditUserClickListener(user: Users) {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
    }

    override fun onItemClickListener(user: Users) {
        TODO("Not yet implemented")
    }
}
