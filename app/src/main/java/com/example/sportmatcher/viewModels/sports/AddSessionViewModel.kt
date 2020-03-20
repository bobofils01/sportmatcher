package com.example.sportmatcher.viewModels.sports


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session

class AddSessionViewModel: ViewModel() {
    val totalNbPlayers by lazy {MutableLiveData<Int>()}
    val nbPlayersSigned by lazy {MutableLiveData<Int>()}
    val price by lazy {MutableLiveData<Double>()}
    lateinit var pitch: Pitch

    private val addSessionUseCase by lazy {
        ServiceProvider.addSessionUseCase
    }
    fun onAddSessionClicked() {
        val s = Session(pitch = pitch.uid, totalNbPlayers = totalNbPlayers.value!!, nbPlayersSigned = nbPlayersSigned.value!!, price = price.value.toString().toDouble())
        Log.d("AddSesVMonAddSesClicked", s.toMap().toString())
        addSessionUseCase.execute(s).subscribe{ session ->
            Log.d("AddSesVMonAddSesClickeR", session.toMap().toString())
        }
    }

}