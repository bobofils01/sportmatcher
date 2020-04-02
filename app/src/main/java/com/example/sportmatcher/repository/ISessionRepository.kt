package com.example.sportmatcher.repository

import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Session
import io.reactivex.Observable
import io.reactivex.Single

interface ISessionRepository {
    fun addSession(session : Session): Single<Session>
    fun updateSession(session : Session): Single<Session >
    fun getAllSessions(): Observable<List<Session>>
    fun getSession(uid: String): Single<Session>
    fun addChatMessage(chatMessage: ChatMessage):Single<Boolean>
    fun getChatMessages(sessionId : String): Observable<List<ChatMessage>>
    fun getParticipants(sessionId: String) : Observable<List<String>>
    fun addParticipant(sessionId: String, participantId:String): Single<Boolean>
    fun removeParticipant(sessionId: String, participantId:String): Single<Boolean>
}