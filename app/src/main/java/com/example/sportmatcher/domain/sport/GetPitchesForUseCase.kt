package com.example.sportmatcher.domain.sport

import android.util.Log
import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.FirebaseSportsRepository
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISportsRepository
import io.reactivex.Observable
import io.reactivex.Single

class GetPitchesForUseCase(private val iPitchesRepository: IPitchesRepository, private val iSportsRepository: ISportsRepository):
    UseCase<String, Observable<List<Pitch>>> {

    //GetAllPitches for the specific sport
    override fun execute(sportID : String): Observable<List<Pitch>> {
        return Observable.create { emitter ->
            //get all pitches id in the sport
            iSportsRepository.getPitchesOfSport(sportID.toLowerCase()).subscribe{
                val pitches = ArrayList<Pitch>()
                //for each pitchiD get the pitch related
                if(it.isEmpty())
                    emitter.onNext(pitches)

                for(pitchID in it){
                    iPitchesRepository.getPitch(pitchID).subscribe{ pitch->
                        pitches.add(pitch)
                        if(pitches.size == it.size)
                            emitter.onNext(pitches)
                    }
                }
            }
        }
    }

}