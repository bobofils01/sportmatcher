package com.example.sportmatcher.repository

import com.example.sportmatcher.model.sport.Pitch
import io.reactivex.Observable

interface ISportsRepository {
    fun getAllSports(): Observable<ArrayList<String>>
    fun updateAllSports( list : ArrayList<String>)
    fun addSport(sport : String)
    fun addPitchToSport( pitch : Pitch)
    fun getPitchesOfSport(sportID: String): Observable<ArrayList<String>>

}
