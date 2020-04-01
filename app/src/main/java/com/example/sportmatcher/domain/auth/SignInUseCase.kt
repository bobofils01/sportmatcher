package com.example.sportmatcher.domain.auth

import android.util.Log
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.domain.utils.isPasswordValid
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.model.authentication.NotAuthenticated
import com.example.sportmatcher.model.notifications.NotificationType
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Single

class SignInUseCase(private val iAuthService: IAuthService) :
    UseCase<LoginInfo, Single<AuthenticationState>> {

    private fun verifyLogin(loginInfo: LoginInfo): Boolean {
        return (loginInfo.email.isEmailValid())
    }

    override fun execute(payload: LoginInfo): Single<AuthenticationState> {
        Log.d("SignInUseCaseTest", (!verifyLogin(payload)).toString()+ " "+ payload.toString())
        if (!verifyLogin(payload)) {
            //TODO remplace par le state
            return Single.error(IllegalStateException("Invalid sign in payload"))
        }
        return iAuthService.signIn(payload.email!!, payload.userPassWord!!).map{
            //TODO for test Send notification
            ServiceProvider.getAuthenticatedState.execute()
            it
        }.onErrorResumeNext(Single.just(NotAuthenticated))
    }
}