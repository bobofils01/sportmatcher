package com.example.sportmatcher.domain.sport

import android.util.Log
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.dto.sport.AddSessionToPitchDTO
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISessionRepository
import io.reactivex.Single

class AddSessionUseCase(private val iSessionRepository: ISessionRepository,
                        private val addSessionToPitchUseCase: AddSessionToPitchUseCase):
    UseCase<Session, Single<Session>> {

    override fun execute(payload: Session): Single<Session> {
        // TODO TRANSACTION!!!!
        return Single.create { emitter ->
            iSessionRepository.addSession(payload).subscribe { session ->
                val dto = AddSessionToPitchDTO(session.uid!!, payload.pitch!!)
                addSessionToPitchUseCase.execute(dto).subscribe { p ->
                    var pitch: Pitch = p
                    if (pitch.uid.isNullOrBlank()) {
                        emitter.onError(error(IllegalStateException("Invalid pitch id")))
                    }
                    Log.d("ADDSESSIONTOPITCH", p.toMap().toString())
                    emitter.onSuccess(session)
                }
            }
        }
    }
}