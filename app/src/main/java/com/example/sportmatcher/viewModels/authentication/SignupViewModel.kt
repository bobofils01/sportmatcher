package com.example.sportmatcher.viewModels.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.service.repository.UserRepository


class SignupViewModel: ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPassword = MutableLiveData<String>()

    private val userRepository: UserRepository = UserRepository()


    fun onSignupClicked(): LiveData<String> {
        if (email.value != null && password.value != null && confirmPassword.value != null) {
            val response = userRepository.signup(email.value!!,password.value!!,confirmPassword.value!!)
            return response
        } else {
            return MutableLiveData<String>()
        }
    }
}