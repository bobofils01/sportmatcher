package com.example.sportmatcher.domain.sport

import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.IPitchesRepository
import io.reactivex.Observable
import io.reactivex.Single

class GetAllPitchesUseCase(private val iPitchesRepository: IPitchesRepository):
    NoInputUseCase<Observable<Pitch>> {

    //GetAllPitches
    override fun execute(): Observable<Pitch> {
        return iPitchesRepository.getAllPitches()
    }




}