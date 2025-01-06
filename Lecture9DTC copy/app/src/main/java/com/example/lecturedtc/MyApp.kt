package com.example.lecturedtc

import android.app.Application
import androidx.room.Room
import com.example.lecturedtc.data.AppDatabase
import com.example.lecturedtc.data.UserRepository

class MyApp: Application() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-database"
        ).allowMainThreadQueries().build()
    }

    val userRepository by lazy {
        UserRepository(db.userDao())
    }
}