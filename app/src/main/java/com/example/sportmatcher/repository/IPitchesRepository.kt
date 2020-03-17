package com.example.sportmatcher.repository

import com.example.sportmatcher.model.sport.Pitch
import io.reactivex.Observable
import io.reactivex.Single

interface IPitchesRepository {

    fun addPitch(pitch : Pitch): Single<Pitch>
    fun updatePitch(pitch : Pitch): Single<Pitch>
    fun getAllPitches(): Observable<List<Pitch>>
    fun getPitchesFor( sportID : String) : Observable<List<Pitch>>
    fun addPitchFor( sportID: String, pitch: Pitch) : Single<Pitch>

}