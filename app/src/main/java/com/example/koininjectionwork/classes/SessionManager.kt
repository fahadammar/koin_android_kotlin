package com.example.koininjectionwork.classes

// SessionManager.kt
class SessionManager {
    private var sessionId: String = "Empty"

    fun createSession(id: String) {
        sessionId = id
    }

    fun getSession(): String {
        return sessionId
    }
}
