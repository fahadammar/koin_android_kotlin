package com.example.koininjectionwork.data.local

import android.app.Application
import androidx.room.Room
import com.example.koininjectionwork.data.local.dao.UserDao
import com.example.koininjectionwork.data.local.db.UserDatabase

fun providesDatabase(application: Application): UserDatabase =
    Room.databaseBuilder(
        application.applicationContext,
        UserDatabase::class.java,
        "userdatabase"
    )
        .fallbackToDestructiveMigration()
        .build()


fun provideDao(db: UserDatabase):UserDao = db.getUserDao()