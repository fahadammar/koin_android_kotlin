package com.example.koininjectionwork.data.api.apiViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koininjectionwork.data.api.service.ApiService
import com.example.koininjectionwork.data.local.dao.UserDao
import com.example.koininjectionwork.data.local.entity.User
import kotlinx.coroutines.launch

class ApiViewModel constructor(
    private val apiService: ApiService,
    private val dao : UserDao
) : ViewModel(){
    fun getPost() = viewModelScope.launch {
        try {
            apiService.getPost()
        } catch (e: Exception){
            Log.d("apiException", "getPost: Exc -> ${e.message}")
        }
    }

    fun insert(user : User) = viewModelScope.launch {
        dao.insert(user)
    }
}