package com.example.koininjectionwork.classes

import android.util.Log
import com.example.koininjectionwork.utils.logTag

class Server(private val name : String) {
    fun connect(){
        Log.d(logTag, "connect -> to server $name")
    }
}