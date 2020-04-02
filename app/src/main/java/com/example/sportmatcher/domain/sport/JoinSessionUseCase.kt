package com.example.sportmatcher.domain.sport

import android.util.Log
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.dto.sport.ParticipantSessionDTO
import com.example.sportmatcher.repository.ISessionRepository
import io.reactivex.Single

class JoinSessionUseCase(private val iSessionRepository: ISessionRepository):
    UseCase<ParticipantSessionDTO, Single<Boolean>> {

    override fun execute(payload: ParticipantSessionDTO): Single<Boolean> {
        return Single.create { emitter ->
            iSessionRepository.addParticipant(payload.sessionID,payload.participantID).subscribe { answer ->
                emitter.onSuccess(answer)
            }

            emitter.onSuccess(true)
        }
    }
}