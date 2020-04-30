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
import kotlinx.android.synthetic.main.add_session_info_layout.*
import kotlinx.coroutines.awaitAll
import java.util.*
import kotlin.collections.ArrayList

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

                if(it.isEmpty())
                    emitter.onNext(sessions)

                for(i in it.indices){
                    iSessionRepository.getSession(it[i]).subscribe{ session->
                        sessions.add(session)
                        if(i == it.size - 1) {
                            val filteredSessions = sessions.filter{!isExpiredDate(it)}
                            emitter.onNext(filteredSessions)
                        }
                    }
                }
            }

        }
    }

    private fun isExpiredDate(sess: Session): Boolean {
        val c = Calendar.getInstance()
        var currentYear = c.get(Calendar.YEAR)
        var currentMonth = c.get(Calendar.MONTH) +1 //android month start from 0
        var currentDay = c.get(Calendar.DAY_OF_MONTH)
        val date = sess.date
        if(!date.isNullOrBlank()){
            val spr = date.split("/")
            val day = spr[0].toInt()
            val month = spr[1].toInt()
            val year = spr[2].toInt()

            if(currentYear > year)
                return true
            else if(currentYear == year && currentMonth > month)
                return true
            else if(currentYear == year && currentMonth == month && currentDay > day)
                return true
        }
        return false
    }
}