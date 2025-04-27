package com.example.koininjectionwork.classes

import android.util.Log
import com.example.koininjectionwork.utils.logTag


class SessionManager {
    private var sessionId: String = "Empty"

    fun createSession(id: String) {
        sessionId = id
    }

    fun getSession(): String {
        Log.d(logTag, "getSession: Called SessionManager")
        return sessionId
    }
}
