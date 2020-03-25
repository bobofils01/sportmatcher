package com.example.sportmatcher.domain.auth

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Single

class SignOutUseCase(private val iAuthService: IAuthService) :
    UseCase<LoginInfo, Single<AuthenticationState>>{

    override fun execute(playload: LoginInfo): Single<AuthenticationState> {
        return iAuthService.logout()
    }
}