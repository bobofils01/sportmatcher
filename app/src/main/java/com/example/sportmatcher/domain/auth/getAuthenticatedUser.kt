package com.example.sportmatcher.domain.auth

import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.NotAuthenticated
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class GetAuthenticatedUserUserCase(private val authService:IAuthService) :
    NoInputUseCase<AuthenticationState> {

    private val authenticationState: MutableLiveData<AuthenticationState> by lazy {
        MutableLiveData<AuthenticationState>(NotAuthenticated)
    }


    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    
    private fun initAuthenticationStateUseCase() {
        compositeDisposable.add(authService.getAuthenticationState().subscribe {
            authenticationState.value = it
        })
    }

    init {
        initAuthenticationStateUseCase()
    }

    override fun execute(): AuthenticationState {
        return authenticationState.value!!
    }
}