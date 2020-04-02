package com.example.sportmatcher.domain.sport

import android.util.Log
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.dto.sport.AddSessionToPitchDTO
import com.example.sportmatcher.dto.sport.ParticipantSessionDTO
import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISessionRepository
import io.reactivex.Single

class QuitSessionUseCase(private val iSessionRepository: ISessionRepository):
    UseCase<ParticipantSessionDTO, Single<Boolean>> {

    override fun execute(payload: ParticipantSessionDTO): Single<Boolean> {
        return Single.create { emitter ->
            iSessionRepository.removeParticipant(payload.sessionID, payload.participantID).subscribe { answer ->
                emitter.onSuccess(answer)
            }
        }
    }
}