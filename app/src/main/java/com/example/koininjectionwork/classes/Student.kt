package com.example.koininjectionwork.classes

import android.util.Log

class Student(private val name:String, private val gender : String, private val age:Int) {
    fun getStudentInfo(){
        Log.d("studentInfo","Name : $name")
        Log.d("studentInfo","Gender : $gender")
        Log.d("studentInfo","Age : $age")
    }
}