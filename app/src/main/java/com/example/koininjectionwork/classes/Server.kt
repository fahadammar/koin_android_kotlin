package com.example.koininjectionwork.classes

class Server(private val name : String) {
    fun connect(){
        println("Connected to server $name")
    }
}