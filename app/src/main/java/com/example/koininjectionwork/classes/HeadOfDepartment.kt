package com.example.koininjectionwork.classes

import android.util.Log

class HeadOfDepartment(private val name:String,
                       private val department: String,
                       private val salary : Int) {

    fun getHODInfo() {
        Log.d("HODInfo","Name : $name")
        Log.d("HODInfo","Department : $department")
        Log.d("HODInfo","Salary : $salary")
    }
}