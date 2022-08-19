package com.example.wasokodemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KParameter

@Database(entities = [Users::class], version = 1)
abstract class UserDB: RoomDatabase() {

    abstract fun userDao(): UserDao

    private var INSTANCE: UserDB? = null
    open fun destroyInstance() {
        INSTANCE = null
    }

    companion object {
        @Volatile private var instance: UserDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            UserDB::class.java, "users.db")
            .build()

        fun getAppDatabase(context: Context): UserDB {
             var INSTANCE: UserDB? = null
            if ( INSTANCE == null){
                INSTANCE = Room.databaseBuilder<UserDB>(
                    context.applicationContext,UserDB::class.java,"AppDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

}

