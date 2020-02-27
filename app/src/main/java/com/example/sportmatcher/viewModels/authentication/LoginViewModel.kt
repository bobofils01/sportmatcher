package com.example.sportmatcher.viewModels.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.service.repository.UserRepository


class LoginViewModel: ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private val userRepository : UserRepository = UserRepository()

    fun onLoginClicked(): LiveData<String> {

        return if(email.value !=null && password.value  != null) {
            val response = userRepository.login(LoginInfo(email.value!!, password.value!!))
            response
        }else{

            MutableLiveData<String>()
        }
    }

}