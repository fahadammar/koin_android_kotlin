package com.example.koininjectionwork.classes

import android.util.Log
import com.example.koininjectionwork.ui.MainActivity
import com.example.koininjectionwork.utils.logTag

class HeadOfDepartment(private val dataObj : MainActivity.HeadOfDepartmentInfo) {

    fun getHODInfo() {
        Log.d(logTag,"HODInfo- Name : ${dataObj.name}")
        Log.d(logTag,"HODInfo- Department : ${dataObj.department}")
        Log.d(logTag,"HODInfo- Salary : ${dataObj.salary}")
    }
}