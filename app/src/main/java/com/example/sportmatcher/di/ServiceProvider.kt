package com.example.sportmatcher.di

import com.example.sportmatcher.domain.auth.*
import com.example.sportmatcher.domain.sport.GetAllSportsUseCase
import com.example.sportmatcher.domain.friendship.*
import com.example.sportmatcher.domain.notifications.RegisterToNotificationsUseCase
import com.example.sportmatcher.domain.preferences.UpdateSportsFavouriteUseCase
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
    private val firebaseSportsRepository :ISportsRepository = FirebaseSportsRepository()
    //services
    val authService:IAuthService = FirebaseAuthService()
    val firebaseMessagingService:INotificationService =FirebaseMessagingService()
    //usecases
    //auth
    val getUserUseCase = GetUserUseCase(firebaseUserRepo)
    val signInUseCase = SignInUseCase(authService)
    val signUpUseCase = SignUpUseCase(authService)
    val signOutUseCase = SignOutUseCase(authService)


    //state
    val getAuthenticatedState = GetAuthenticationStateUseCase(authService)
    val getAuthenticatedUserUseCase = GetAuthenticatedUserUserCase(authService)

    //notifications
    val registerToNotificationsUseCase = RegisterToNotificationsUseCase(notificationRepo, firebaseMessagingService)

    //sports
    val getAllSportsUseCase = GetAllSportsUseCase(firebaseSportsRepository)

    //pitches
    val addPitchUseCase = AddPitchUseCase(firebasePitchesRepo, firebaseSportsRepository)
    val getAllPitchesUseCase = GetAllPitchesUseCase(firebasePitchesRepo)
    val getPitchesForUseCase= GetPitchesForUseCase(firebasePitchesRepo, firebaseSportsRepository)
    val getPitchUseCase = GetPitchUseCase(firebasePitchesRepo)

    //sessions
    val addSessionUseCase = AddSessionUseCase(firebaseSessionRepo, firebasePitchesRepo)
    val getAllSessionsUseCase = GetAllSessionsUseCase(firebaseSessionRepo)
    val joinSessionUseCase = JoinSessionUseCase(firebaseSessionRepo)
    val getParticipantsForASessionUseCase = GetParticipantsForASessionUseCase(firebaseSessionRepo)
    val quitSessionUseCase = QuitSessionUseCase(firebaseSessionRepo)

    //pitches and sessions
    val getAllSessionsForAPitchUseCase = GetAllSessionsForAPitchUseCase(firebasePitchesRepo,
        firebaseSessionRepo)

    // chat
    val sendMessageUseCase = SendMessageUseCase(firebaseSessionRepo)
    val getChatMessagesForASession = GetChatMessagesForASessionUseCase(firebaseSessionRepo)

    //preferences
    val updateSportsFavouriteUseCase = UpdateSportsFavouriteUseCase(firebaseUserRepo, firebaseMessagingService)

    //friendship
    val addFriendUseCase = AddFriendUseCase(firebaseUserRepo)
    val getFriendsUseCase = GetFriendsUseCase(firebaseUserRepo)
    val getAllUsersUseCase = GetAllUsersUseCase(firebaseUserRepo)
    val deleteFriendUseCase = DeleteFriendUseCase(firebaseUserRepo)
}