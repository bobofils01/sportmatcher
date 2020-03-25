package com.example.sportmatcher.domain.sport

import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.repository.ISessionRepository
import io.reactivex.Observable

class GetAllSessionsUseCase(private val iSessionRepository: ISessionRepository)
    : NoInputUseCase<Observable<List<Session>>> {

    override fun execute(): Observable<List<Session>> {
        return iSessionRepository.getAllSessions()
    }
}