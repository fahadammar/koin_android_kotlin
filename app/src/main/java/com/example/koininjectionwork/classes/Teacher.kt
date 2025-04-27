package com.example.koininjectionwork.classes

import android.util.Log

class Teacher(
    private val name:String,
    private val subject: String,
    private val creditHours : Double,
    private val department: String,
    private val salary : Int
) {
    fun getTeacherInfo(){
        Log.d("teacherInfo","Name : $name")
        Log.d("teacherInfo","Subject : $subject")
        Log.d("teacherInfo","Credit Hours : $creditHours")
        Log.d("teacherInfo","Department : $department")
        Log.d("teacherInfo","Salary : $salary")
    }
}