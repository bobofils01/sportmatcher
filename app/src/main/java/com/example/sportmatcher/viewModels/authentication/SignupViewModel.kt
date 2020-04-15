package com.example.sportmatcher.viewModels.authentication

import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.SignupInfo
import com.example.sportmatcher.viewModels.AbstractViewModel


class SignupViewModel: AbstractViewModel() {

    val firstName by lazy { MutableLiveData<String>() }
    val lastName by lazy { MutableLiveData<String>() }
    val email by lazy { MutableLiveData<String>() }
    val password by lazy { MutableLiveData<String>() }

    private val signUpUseCase by lazy{
        ServiceProvider.signUpUseCase
    }

    fun onRegisterClicked() {
        compositeDisposable.add(
            signUpUseCase.execute(
                SignupInfo(
                    firstName.value,
                    lastName.value,
                    email.value,
                    password.value
                )
            ).subscribe()
        )
    }
}