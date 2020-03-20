package com.example.sportmatcher.domain.sport

import android.service.voice.AlwaysOnHotwordDetector
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.IPitchesRepository
import io.reactivex.Single

class GetPitchUseCase(private val iPitchesRepository: IPitchesRepository):
    UseCase<String?, Single<Pitch>> {

    override fun execute(payload: String?): Single<Pitch> {
        if(payload.isNullOrBlank()){
            return Single.error(IllegalStateException("Invalid pitch id"))
        }
        return iPitchesRepository.getPitch(payload)
    }
}