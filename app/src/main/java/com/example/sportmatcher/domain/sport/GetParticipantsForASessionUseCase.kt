package com.example.sportmatcher.domain.sport

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISessionRepository
import com.google.android.gms.tasks.Tasks.await
import io.reactivex.Observable
import kotlinx.coroutines.awaitAll

class GetParticipantsForASessionUseCase(private val iSessionRepository: ISessionRepository)
    : UseCase<Session, Observable<List<User>>> {

    override fun execute(payload: Session): Observable<List<User>> {
        if (payload.uid.isNullOrBlank()) {
            return Observable.error(IllegalStateException("Invalid sign in payload"))
        }
        return  Observable.create{ emitter ->
            iSessionRepository.getParticipants(payload.uid!!).subscribe{
                val participants = ArrayList<User>()
                if(it.isEmpty())
                    emitter.onNext(participants)

                for(i in it.indices){
                    ServiceProvider.getUserUseCase.execute(it[i]).subscribe{ user ->
                        participants.add(user)
                        if(i == it.size - 1)
                            emitter.onNext(participants)
                    }
                }
            }
        }

    }
}