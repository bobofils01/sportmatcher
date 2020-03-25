package com.example.sportmatcher.repository

import com.example.sportmatcher.dto.sport.AddSessionToPitchDTO
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import io.reactivex.Observable
import io.reactivex.Single

interface IPitchesRepository {

    fun addPitch(pitch : Pitch): Single<Pitch>
    fun updatePitch(pitch : Pitch): Single<Pitch>
    fun getAllPitches(): Observable<List<Pitch>>
    fun getPitch(uid: String): Single<Pitch>
    fun addSessionToPitch(addSessionToPitchDTO: AddSessionToPitchDTO): Single<Pitch>
    fun getAllSessionsForAPitch(uid: String): Observable<List<String>>
}