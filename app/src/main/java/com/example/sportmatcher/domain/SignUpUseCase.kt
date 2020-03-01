package com.example.sportmatcher.domain

import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.domain.utils.isPasswordValid
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.model.authentication.SignupInfo
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Single

class SignUpUseCase(private val iAuthService: IAuthService) : UseCase<SignupInfo, Single<AuthenticationState>> {

    override fun execute(payload: SignupInfo): Single<AuthenticationState> {
        if (verifyPayload(
                payload.email,
                payload.passWord,
                payload.confirmPassword
            ).isNullOrBlank()
        ) {
            return Single.error(IllegalStateException("Invalid sign up payload"))
        }
        return iAuthService.register(payload.email!!, payload.passWord!!)
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