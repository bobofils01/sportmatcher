package com.example.sportmatcher.viewModels.authentication

import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.viewModels.AbstractViewModel

class LogOutViewModel: AbstractViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

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