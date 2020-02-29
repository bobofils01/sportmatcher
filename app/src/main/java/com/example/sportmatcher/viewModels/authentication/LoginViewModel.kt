package com.example.sportmatcher.viewModels.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.service.FirebaseAuthService


class LoginViewModel: ViewModel() {

    val email by lazy { MutableLiveData<String>() }
    val password by lazy { MutableLiveData<String>() }
    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService
    fun onLoginClicked(): LiveData<String> {

        return if(email.value !=null && password.value  != null) {
            val response = firebaseAuthService.login(LoginInfo(email.value!!, password.value!!))
            response
        }else{
            MutableLiveData<String>()
        }
    }

}