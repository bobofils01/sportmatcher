package com.example.sportmatcher.viewModels.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.service.FirebaseAuthService


class SignupViewModel: ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPassword = MutableLiveData<String>()

    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService
    fun onSignupClicked(): LiveData<String> {
        if (email.value != null && password.value != null && confirmPassword.value != null) {
            val response =
                firebaseAuthService.signup(email.value!!, password.value!!, confirmPassword.value!!)
            Log.i("f", "$response")
            return response
        } else {
            return MutableLiveData<String>()
        }
    }
}