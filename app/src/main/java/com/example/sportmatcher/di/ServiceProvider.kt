package com.example.sportmatcher.di

import com.example.sportmatcher.domain.GetAuthenticationStateUseCase
import com.example.sportmatcher.domain.SignInUseCase
import com.example.sportmatcher.domain.SignUpUseCase
import com.example.sportmatcher.service.FirebaseAuthService
import com.example.sportmatcher.service.IAuthService

object ServiceProvider {



    val authService:IAuthService = FirebaseAuthService()
    val getAuthenticatedState = GetAuthenticationStateUseCase(authService)
    val signInUseCase = SignInUseCase(authService)
    val signUpUseCase = SignUpUseCase(authService)


}