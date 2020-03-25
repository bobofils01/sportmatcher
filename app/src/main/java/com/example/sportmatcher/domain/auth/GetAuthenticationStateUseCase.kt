package com.example.sportmatcher.domain.auth

import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.service.IAuthService
import io.reactivex.Observable

class GetAuthenticationStateUseCase(private val authService:IAuthService) :
    NoInputUseCase<Observable<AuthenticationState>> {

    override fun execute(): Observable<AuthenticationState> {
        ServiceProvider.firebaseMessagingService.getCurrentNotificationToken()
        return authService.getAuthenticationState()
    }

}