package com.example.sportmatcher.model.authentication

import com.example.sportmatcher.model.User

sealed class AuthenticationState

data class AuthenticatedState(val user:User):AuthenticationState()
object AuthenticationInProgress:AuthenticationState()
object NotAuthenticated:AuthenticationState()


