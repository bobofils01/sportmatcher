package com.example.sportmatcher.domain.sport


import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.repository.ISportsRepository
import io.reactivex.Observable

class GetAllSportsUseCase(val firebaseSportsRepository: ISportsRepository) : NoInputUseCase<Observable<ArrayList<String>>> {

    override fun execute(): Observable<ArrayList<String>> {
        return firebaseSportsRepository.getAllSports()
    }

}
