package com.example.sportmatcher.domain

import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Observable

class GetAuthenticationStateUseCase(private val authService:IAuthService) : NoInputUseCase<Observable<AuthenticationState>> {

    override fun execute(): Observable<AuthenticationState> {
        return authService.getAuthenticationState()
    }

}