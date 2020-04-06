package com.example.sportmatcher.domain.auth

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import androidx.core.os.postDelayed
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.R
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.getUserUseCase
import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.domain.friendship.GetUserUseCase
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
    NoInputUseCase<User?> {

    private val authenticationState: MutableLiveData<AuthenticationState> by lazy {
        MutableLiveData<AuthenticationState>(NotAuthenticated)
    }

    private val authenticatedUser by lazy {
        MutableLiveData<User>()
    }
    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private fun initAuthenticationStateUseCase() {
        compositeDisposable.add(authService.getAuthenticationState().subscribe {
            authenticationState.value = it
        })
    }


    override fun execute(): User? {
        return authenticatedUser.value!!
    }

    init {
        initAuthenticationStateUseCase()
        authService.getCurrentUser().subscribe{ userUuid ->
            //wait until the user is fetched
            if(!userUuid.isNullOrBlank()) {
                getUserUseCase.execute(userUuid).subscribe { user ->
                    authenticatedUser.value = user

                }
            }
        }

    }
}