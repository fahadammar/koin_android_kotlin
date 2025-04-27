package com.example.koininjectionwork.interfaces

import android.util.Log
import com.example.koininjectionwork.utils.logTag

interface FirstInterface{
    fun printFirstInterface()
}

interface SecondInterface{
    fun printSecondInterface()
}


class SingleInterfaceImpl : FirstInterface{
    override fun printFirstInterface() {
        Log.d(logTag, "printFirstInterface: FIRST INTERFACE PRINT")
    }
}

class AllInterfaceImpl : FirstInterface, SecondInterface{
    override fun printFirstInterface() {
        Log.d(logTag, "printFirstInterface: FIRST INTERFACE PRINT")
    }
    override fun printSecondInterface() {
        Log.d(logTag, "printSecondInterface: SECOND INTERFACE PRINT")
    }
}

class CallSingleInterface(private val firstInterface: FirstInterface){
    fun callInterface(){
        firstInterface.printFirstInterface()
    }
}

class CallAllInterface(private val firstInterface: FirstInterface, private val secondInterface: SecondInterface){
    fun callAllInterfaces(){
        firstInterface.printFirstInterface()
        secondInterface.printSecondInterface()
    }
}

