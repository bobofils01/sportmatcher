package com.example.sportmatcher.viewModels.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.repository.UserRepository


class LoginViewModel: ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private var userRepository : UserRepository = UserRepository()

    fun onLoginClicked(): LiveData<String> {
        Log.i("ee", "email :${email.value} password : ${password.value}")
        val response = userRepository.login(LoginInfo(email.value ,password.value))
        Log.i("e", "answer : ${response.value}")
        return response
    }
}