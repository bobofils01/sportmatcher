package com.example.sportmatcher.di

import com.example.sportmatcher.domain.auth.GetAuthenticationStateUseCase
import com.example.sportmatcher.domain.auth.SignInUseCase
import com.example.sportmatcher.domain.auth.SignUpUseCase
import com.example.sportmatcher.domain.notifications.RegisterToNotificationsUseCase
import com.example.sportmatcher.domain.preferences.UpdateSportsFavouriteUsecase
import com.example.sportmatcher.domain.sport.*
import com.example.sportmatcher.repository.*
import com.example.sportmatcher.service.FirebaseAuthService
import com.example.sportmatcher.service.FirebaseMessagingService
import com.example.sportmatcher.service.IAuthService
import com.example.sportmatcher.service.INotificationService

object ServiceProvider {



    //repo
    private val notificationRepo: INotificationsRepository = FirebaseNotifRepository()
    private val firebasePitchesRepo: IPitchesRepository = FirebasePitchesRepository()
    private val firebaseSessionRepo: ISessionRepository = FirebaseSessionRepository()
    private val firebaseUserRepo : IUserRepository = FirebaseUserRepository()

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
    val registerToNotificationsUseCase = RegisterToNotificationsUseCase(notificationRepo, firebaseMessagingService)

    //pitches
    val addPitchUseCase = AddPitchUseCase(firebasePitchesRepo)
    val getAllPitchesUseCase = GetAllPitchesUseCase(firebasePitchesRepo)
    val getPitchesForUseCase= GetPitchesForUseCase(firebasePitchesRepo)
    val getPitchUseCase = GetPitchUseCase(firebasePitchesRepo)
    val addSessionToPitchUseCase = AddSessionToPitchUseCase(firebasePitchesRepo)

    //sessions
    val addSessionUseCase = AddSessionUseCase(firebaseSessionRepo, addSessionToPitchUseCase)
    val getAllSessionsUseCase = GetAllSessionsUseCase(firebaseSessionRepo)

    //pitches and sessions
    val getAllSessionsForAPitchUseCase = GetAllSessionsForAPitchUseCase(firebasePitchesRepo,
        firebaseSessionRepo)

    //preferences
    val updateSportsFavouriteUsecase = UpdateSportsFavouriteUsecase(firebaseUserRepo, firebaseMessagingService)

}