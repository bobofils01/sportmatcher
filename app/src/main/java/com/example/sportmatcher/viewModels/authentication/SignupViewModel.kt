package com.example.sportmatcher.viewModels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.SignupInfo
import io.reactivex.disposables.CompositeDisposable


class SignupViewModel: ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPassword = MutableLiveData<String>()

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val signupUseCase by lazy{
        ServiceProvider.signUpUseCase
    }

    fun onRegisterClicked() {
        compositeDisposable.add(
            signupUseCase.execute(
                SignupInfo(
                    email.value,
                    password.value,
                    confirmPassword.value
                )
            ).subscribe()
            )

    }
}