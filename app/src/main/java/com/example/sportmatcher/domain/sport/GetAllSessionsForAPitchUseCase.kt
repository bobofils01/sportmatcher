package com.example.sportmatcher.domain.sport

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISessionRepository
import com.google.android.gms.tasks.Tasks.await
import io.reactivex.Observable
import kotlinx.coroutines.awaitAll

class GetAllSessionsForAPitchUseCase(private val iPitchesRepository: IPitchesRepository,
                                     private val iSessionRepository: ISessionRepository)
    : UseCase<Pitch, Observable<List<Session>>> {

    override fun execute(payload: Pitch): Observable<List<Session>> {
        if (payload.uid.isNullOrBlank()) {
            return Observable.error(IllegalStateException("Invalid sign in payload"))
        }
        return Observable.create { emitter ->

            iPitchesRepository.getAllSessionsForAPitch(payload.uid!!).subscribe {
                val sessions = ArrayList<Session>()
                for(i in it.indices){
                    iSessionRepository.getSession(it[i]).subscribe{ session->
                        sessions.add(session)
                        Log.d("ZZZAllSessionsForAPitch", session.toMap().toString())

                        if(i == it.size - 1)
                            emitter.onNext(sessions)
                    }
                }
            }

        }
    }
}