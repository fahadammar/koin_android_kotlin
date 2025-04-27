package com.example.koininjectionwork.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.koininjectionwork.data.local.dao.UserDao
import com.example.koininjectionwork.data.local.entity.User

@Database(entities = [User::class],version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao():UserDao
}