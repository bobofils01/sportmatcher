package com.example.sportmatcher.di

import com.example.sportmatcher.domain.auth.GetAuthenticationStateUseCase
import com.example.sportmatcher.domain.notifications.SendPushNotificationsUseCase
import com.example.sportmatcher.domain.auth.SignInUseCase
import com.example.sportmatcher.domain.auth.SignUpUseCase
import com.example.sportmatcher.domain.notifications.RegisterToNotificationsUseCase
import com.example.sportmatcher.repository.FirebaseNotifRepository
import com.example.sportmatcher.repository.INotificationsRepository
import com.example.sportmatcher.service.FirebaseAuthService
import com.example.sportmatcher.service.FirebaseMessagingService
import com.example.sportmatcher.service.IAuthService
import com.example.sportmatcher.service.INotificationService

object ServiceProvider {



    //repo
    val notificationRepo: INotificationsRepository = FirebaseNotifRepository()

    //services
    val authService:IAuthService = FirebaseAuthService()
    val firebaseMessagingService:INotificationService =FirebaseMessagingService()

    //state
    val getAuthenticatedState = GetAuthenticationStateUseCase(authService)

    //usecases
    //auth
    val signInUseCase = SignInUseCase(authService)
    val signUpUseCase = SignUpUseCase(authService)

    //notifications
    val sendPushNotificationUseCase = SendPushNotificationsUseCase(firebaseMessagingService, notificationRepo)
    val registerToNotificationsUseCase = RegisterToNotificationsUseCase(notificationRepo, firebaseMessagingService)

}