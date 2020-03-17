package com.example.sportmatcher.domain.sport

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.dto.sport.AddPitchDTO
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.IPitchesRepository
import io.reactivex.Single

class AddPitchForUseCase(private val iPitchesRepository: IPitchesRepository):
    UseCase<AddPitchDTO, Single<Pitch>>{

    override fun execute(payload: AddPitchDTO): Single<Pitch> {
        return iPitchesRepository.addPitchFor(payload.sportID, payload.pitch)
    }


}