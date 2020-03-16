package com.example.sportmatcher.repository

import com.example.sportmatcher.model.sport.Session
import io.reactivex.Observable
import io.reactivex.Single

interface ISessionRepository {
    fun addSession(session : Session): Single<Session>
    fun updateSession(session : Session): Single<Session >
    fun getAllSessions(): Observable<List<Session>>
}