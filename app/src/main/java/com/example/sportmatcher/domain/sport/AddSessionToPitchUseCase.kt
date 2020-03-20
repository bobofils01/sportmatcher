package com.example.sportmatcher.domain.sport

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.dto.sport.AddSessionToPitchDTO
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.IPitchesRepository
import io.reactivex.Single

class AddSessionToPitchUseCase(private val iPitchesRepository: IPitchesRepository):
    UseCase<AddSessionToPitchDTO, Single<Pitch>>{

    override fun execute(payload: AddSessionToPitchDTO): Single<Pitch> {
        //TODO return iPitchesRepository.addPitchFor(payload.sportID, payload.pitch)
        return iPitchesRepository.addSessionToPitch(payload)
    }


}