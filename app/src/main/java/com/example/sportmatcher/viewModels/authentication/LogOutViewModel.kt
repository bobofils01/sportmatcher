package com.example.sportmatcher.viewModels.authentication

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.model.authentication.SignupInfo
import io.reactivex.disposables.CompositeDisposable

class LogOutViewModel: ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val signOutUseCase by lazy{
        ServiceProvider.signOutUseCase
    }

    fun onLogoutClicked(){
        compositeDisposable.add(
            signOutUseCase.execute(
                LoginInfo(
                    email.value,
                    password.value
                )
            ).subscribe()
        )
    }
}