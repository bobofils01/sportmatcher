package com.example.sportmatcher.viewModels.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.repository.UserRepository


class LoginViewModel: ViewModel() {

    /**
    Inutile d'en faire des variables, dans ce cas ci c'est bon si c'est immuable
    car on va créer le MutableLiveDate une fois et update la valeur plusieurs fois

    val + lazy -> C'est la vie
     */
    val email by lazy {
        MutableLiveData<String>()
    }
    val password by lazy {
        MutableLiveData<String>()
    }

    /**
     * Inutile de préciser les types sauf si c'est nécessaire pour interfacer/abstraire, Kotlin le fait à la compilation
     */
    private val userRepository by lazy {
        UserRepository()
    }

    fun onLoginClicked(): LiveData<String> {
        Log.i("ee", "email :${email.value} password : ${password.value}")
        val response = userRepository.login(LoginInfo(email.value, password.value))
        Log.i("e", "answer : ${response.value}")
        return response
    }
}