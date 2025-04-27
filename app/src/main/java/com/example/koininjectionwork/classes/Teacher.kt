package com.example.koininjectionwork.classes

import android.util.Log
import com.example.koininjectionwork.utils.logTag

class Teacher(
    private val name:String,
    private val subject: String,
    private val creditHours : Double,
    private val department: String,
    private val salary : Int
) {
    fun getTeacherInfo(){
        Log.d(logTag,"TeacherInfo-Name : $name")
        Log.d(logTag,"TeacherInfo-Subject : $subject")
        Log.d(logTag,"TeacherInfo-Credit Hours : $creditHours")
        Log.d(logTag,"TeacherInfo-Department : $department")
        Log.d(logTag,"TeacherInfo-Salary : $salary")
    }
}