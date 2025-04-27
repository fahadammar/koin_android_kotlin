package com.example.koininjectionwork.classes

import android.util.Log

class Airplane(private val wings: Wings, private val pilot: Pilot) {
    fun fly(){
        pilot.isPilotReady()
        wings.wingsOpen()
        Log.d("airplaneClassTag", "fly: AIR PLANE IS FLYING")
    }
}

class Wings(){
    fun wingsOpen(){
        Log.d("airplaneClassTag", "wings: Wings Are Open To Fly")
    }
}

class Pilot(){
    fun isPilotReady(){
        Log.d("airplaneClassTag", "pilot: Yes Pilot is Ready")
    }
}