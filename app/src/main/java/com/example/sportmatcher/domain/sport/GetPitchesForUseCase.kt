package com.example.sportmatcher.domain.sport

import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.IPitchesRepository
import io.reactivex.Observable
import io.reactivex.Single

class GetPitchesForUseCase(private val iPitchesRepository: IPitchesRepository):
    UseCase<String, Observable<List<Pitch>>> {

    //GetAllPitches for the specific sport
    override fun execute( sportID : String): Observable<List<Pitch>> {
        return iPitchesRepository.getPitchesFor(sportID)
    }

}