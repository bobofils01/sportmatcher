package com.example.sportmatcher.viewModels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.SignupInfo
import io.reactivex.disposables.CompositeDisposable


class SignupViewModel: ViewModel() {

    var firstName = MutableLiveData<String>()
    var lastName = MutableLiveData<String>()
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
                    firstName.value,
                    lastName.value,
                    email.value,
                    password.value,
                    confirmPassword.value
                )
            ).subscribe()
            )

    }
}