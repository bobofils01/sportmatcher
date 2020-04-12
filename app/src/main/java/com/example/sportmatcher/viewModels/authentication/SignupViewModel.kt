package com.example.sportmatcher.viewModels.authentication

import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.SignupInfo
import com.example.sportmatcher.viewModels.AbstractViewModel


class SignupViewModel: AbstractViewModel() {

    lateinit var firstName: String // = MutableLiveData<String>()
    lateinit var lastName: String //= MutableLiveData<String>()
    lateinit var email: String //= MutableLiveData<String>()
    lateinit var password: String //= MutableLiveData<String>()

    private val signUpUseCase by lazy{
        ServiceProvider.signUpUseCase
    }

    fun onRegisterClicked() {
        compositeDisposable.add(
            signUpUseCase.execute(
                SignupInfo(
                    firstName,
                    lastName,
                    email,
                    password
                )
            ).subscribe()
        )
    }
}