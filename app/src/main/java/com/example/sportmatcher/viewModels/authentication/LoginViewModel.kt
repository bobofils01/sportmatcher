package com.example.sportmatcher.viewModels.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.model.authentication.NotAuthenticated
import com.example.sportmatcher.ui.authentication.LoginViewState
import com.example.sportmatcher.viewModels.AbstractViewModel


class LoginViewModel : AbstractViewModel() {

    val email by lazy { MutableLiveData<String>() }
    val password by lazy { MutableLiveData<String>() }

    val loginViewStateLiveData: MutableLiveData<LoginViewState> by lazy {
        MutableLiveData<LoginViewState>(LoginViewState.SIGNIN)
    }

    fun getLoginViewStateLiveData(): LiveData<LoginViewState> = loginViewStateLiveData

    private val authenticationState: MutableLiveData<AuthenticationState> by lazy {
        MutableLiveData<AuthenticationState>(NotAuthenticated)
    }

    fun getAuthenticationStateLiveData(): LiveData<AuthenticationState> = authenticationState


    private val signInUseCase by lazy {
        ServiceProvider.signInUseCase
    }
    private val authenticationStateUseCase by lazy {
        ServiceProvider.getAuthenticatedState
    }

    private fun initAuthenticationStateUseCase() {
        compositeDisposable.add(authenticationStateUseCase.execute().subscribe {
            authenticationState.value = it
        })
    }

    fun onLoginClicked() {
        compositeDisposable.add(
            signInUseCase.execute(
                LoginInfo(
                    email.value,
                    password.value
                )
            ).subscribe()
        )
    }

    fun onSignUpClicked(){
        loginViewStateLiveData.value = LoginViewState.SIGNUP
    }


    init {
        initAuthenticationStateUseCase()
    }
}