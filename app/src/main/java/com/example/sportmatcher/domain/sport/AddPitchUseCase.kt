package com.example.sportmatcher.domain.sport

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.IPitchesRepository
import io.reactivex.Single

class AddPitchUseCase(private val iPitchesRepository: IPitchesRepository):
    UseCase<Pitch, Single<Pitch>>{

    override fun execute(payload: Pitch): Single<Pitch> {
        return iPitchesRepository.addPitch(payload)
    }


}