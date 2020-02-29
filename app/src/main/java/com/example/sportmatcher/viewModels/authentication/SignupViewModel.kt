package com.example.sportmatcher.viewModels.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.SignupInfo
import com.example.sportmatcher.service.FirebaseAuthService
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

    //private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService
    fun onSignupClicked(){
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