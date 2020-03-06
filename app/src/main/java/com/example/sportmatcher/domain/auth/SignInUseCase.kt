package com.example.sportmatcher.domain.auth

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

    private fun verifyLogin(loginInfo: LoginInfo): String? {
        return if (loginInfo.email.isEmailValid()) {
            if (!loginInfo.userPassWord.isPasswordValid() && loginInfo.userPassWord!!.length < 8) {
                "Invalid Password"
            } else {
                null
                "Successful Login"
            }
        } else {
            "Invalid Email"
        }
    }

    override fun execute(payload: LoginInfo): Single<AuthenticationState> {
        if (verifyLogin(payload).isNullOrBlank()) {
            //TODO remplace par le state
            return Single.error(IllegalStateException("Invalid sign in payload"))
        }
        return iAuthService.signIn(payload.email!!, payload.userPassWord!!).map{
            //TODO for test Send notification
            ServiceProvider.sendPushNotificationUseCase.execute(NotificationType.SIGNIN)
            it
        }.onErrorResumeNext(Single.just(NotAuthenticated))
    }
}