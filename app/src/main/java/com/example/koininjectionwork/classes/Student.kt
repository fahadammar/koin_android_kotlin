package com.example.koininjectionwork.classes

import android.util.Log
import com.example.koininjectionwork.utils.logTag

class Student(private val name:String, private val gender : String, private val age:Int) {
    fun getStudentInfo(){
        Log.d(logTag,"Name : $name")
        Log.d(logTag,"Gender : $gender")
        Log.d(logTag,"Age : $age")
    }
}