package com.example.sportmatcher.domain.sport

import android.util.Log
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.dto.sport.AddSessionToPitchDTO
import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISessionRepository
import io.reactivex.Single

class SendMessageUseCase(private val iSessionRepository: ISessionRepository):
    UseCase<ChatMessage, Single<Boolean>> {

    override fun execute(payload: ChatMessage): Single<Boolean> {
        return Single.create { emitter ->
            iSessionRepository.addChatMessage(payload).subscribe { answer ->
                emitter.onSuccess(answer)
            }
        }
    }
}