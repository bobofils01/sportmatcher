package com.example.sportmatcher.viewModels.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.ui.authentication.LoginViewState
import io.reactivex.disposables.CompositeDisposable


class LoginViewModel : ViewModel() {

    val email by lazy { MutableLiveData<String>() }
    val password by lazy { MutableLiveData<String>() }

    private val loginViewStateLiveData: MutableLiveData<LoginViewState> by lazy {
        MutableLiveData<LoginViewState>(LoginViewState.SIGNIN)
    }

    fun getLoginViewStateLiveData(): LiveData<LoginViewState> = loginViewStateLiveData

    private val authenticationState: MutableLiveData<AuthenticationState> by lazy {
        MutableLiveData<AuthenticationState>(AuthenticationInProgress)
    }

    fun getAuthenticationStateLiveDate(): LiveData<AuthenticationState> = authenticationState


    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
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

    override fun onCleared() {
        compositeDisposable.apply {
            dispose()
            clear()
        }
        super.onCleared()
    }

    init {
        initAuthenticationStateUseCase()
    }
}