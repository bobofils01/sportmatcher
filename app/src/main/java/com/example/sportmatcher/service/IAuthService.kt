package com.example.sportmatcher.service

import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.authentication.AuthenticationState
import io.reactivex.Observable
import io.reactivex.Single

interface IAuthService {
    fun signIn(email : String ,password :String): Single<AuthenticationState>
    fun register(email: String ,password: String, firstName: String, lastName: String): Single<AuthenticationState>
    fun logout(): Single<AuthenticationState>
    fun forgotPassword(email: String)
    fun getAuthenticationState(): Observable<AuthenticationState>
    fun getCurrentUser() :Observable<String>
}