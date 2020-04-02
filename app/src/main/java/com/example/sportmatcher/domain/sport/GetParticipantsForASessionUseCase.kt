package com.example.sportmatcher.domain.sport

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISessionRepository
import com.google.android.gms.tasks.Tasks.await
import io.reactivex.Observable
import kotlinx.coroutines.awaitAll

class GetParticipantsForASessionUseCase(private val iSessionRepository: ISessionRepository)
    : UseCase<Session, Observable<List<String>>> {

    override fun execute(payload: Session): Observable<List<String>> {
        if (payload.uid.isNullOrBlank()) {
            return Observable.error(IllegalStateException("Invalid sign in payload"))
        }
        return  iSessionRepository.getParticipants(payload.uid!!)
    }
}