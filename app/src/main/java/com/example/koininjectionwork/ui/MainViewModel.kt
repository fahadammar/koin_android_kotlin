package com.example.koininjectionwork.ui

import androidx.lifecycle.ViewModel
import com.example.koininjectionwork.classes.Student
import com.example.koininjectionwork.classes.UserInfo

class MainViewModel(private val userInfo : UserInfo, private val student: Student) : ViewModel() {
    fun getUserName() = userInfo.userName()
    fun getStudentInfo() = student.getStudentInfo()
}