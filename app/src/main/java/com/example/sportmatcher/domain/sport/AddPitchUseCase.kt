package com.example.sportmatcher.domain.sport

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.IPitchesRepository
import com.example.sportmatcher.repository.ISportsRepository
import io.reactivex.Single

class AddPitchUseCase(private val iPitchesRepository: IPitchesRepository, private val iSportsRepository: ISportsRepository):
    UseCase<Pitch, Single<Pitch>>{

    override fun execute(payload: Pitch): Single<Pitch> {
        return Single.create { emitter ->
            iPitchesRepository.addPitch(payload).subscribe{
                pitch ->
                    //link the pitch to the corresponding sport
                    iSportsRepository.addPitchToSport(pitch)
                    emitter.onSuccess(pitch)
            }
        }
    }


}