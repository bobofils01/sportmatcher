package com.example.sportmatcher.domain.auth

import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.domain.utils.isPasswordValid
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.NotAuthenticated
import com.example.sportmatcher.model.authentication.SignupInfo
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Single

class SignUpUseCase(private val iAuthService: IAuthService) :
    UseCase<SignupInfo, Single<AuthenticationState>> {

    override fun execute(payload: SignupInfo): Single<AuthenticationState> {
        if (verifyPayload(
                payload.email,
                payload.passWord,
                payload.confirmPassword
            ).isNullOrBlank()
        ) {
            return Single.error(IllegalStateException("Invalid sign up payload"))
        }

        return iAuthService.register(payload.email!!, payload.passWord!!).map{
            ServiceProvider.registerToNotificationsUseCase.execute()
            it
        }.onErrorResumeNext(Single.just(NotAuthenticated))
    }


    private fun verifyPayload(email: String?, password: String?, confirmPassword: String?): String? {
        return if (email.isEmailValid()) {
            if (!password.isPasswordValid() && password!!.length < 8 ) {
                "Invalid Password"
            } else if (!password.equals(confirmPassword)) {
                "unmatch password"
            } else {
                null
                "Successful register"
            }
        } else {
            "Invalid Email"
        }
    }

}