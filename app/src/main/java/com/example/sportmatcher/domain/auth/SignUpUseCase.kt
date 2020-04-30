package com.example.sportmatcher.domain.auth

import android.util.Log
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.domain.utils.isNameValid
import com.example.sportmatcher.domain.utils.isPasswordValid
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.NotAuthenticated
import com.example.sportmatcher.model.authentication.SignupInfo
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Single

class SignUpUseCase(private val iAuthService: IAuthService) :
    UseCase<SignupInfo, Single<AuthenticationState>> {

    override fun execute(payload: SignupInfo): Single<AuthenticationState> {
        var verifPayload = verifyPayload(
            payload.firstName,
            payload.lastName,
            payload.email,
            payload.passWord
        )
        Log.d("SignUpUseCaseTest", verifPayload+ " "+ (!verifPayload.isNullOrBlank()).toString())
        if (!verifPayload.isNullOrBlank()) {
            return Single.error(IllegalStateException(verifPayload))
        }

        return iAuthService.register(payload.email!!, payload.passWord!!, payload.firstName!!, payload.lastName!!).map{
            //After signup enable all notifications.
            ServiceProvider.getAllSportsUseCase.execute().subscribe { sports ->
                ServiceProvider.updateSportsFavouriteUseCase.execute(sports)
            }
            it
        }.onErrorResumeNext(Single.just(NotAuthenticated))
    }


    private fun verifyPayload(firstName: String?, lastName: String?, email: String?, password: String?): String? {
        return if(firstName.isNameValid() && lastName.isNameValid()){
            if (email.isEmailValid()) {
                if (!password.isPasswordValid())
                    "Invalid Password"

                else
                    null
            }
            else
                "Invalid Email"
        }
        else
            "Invalid Name"
    }

}