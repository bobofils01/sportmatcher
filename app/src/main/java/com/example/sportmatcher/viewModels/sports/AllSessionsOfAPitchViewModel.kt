package com.example.sportmatcher.viewModels.sports

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session

class AllSessionsOfAPitchViewModel: ViewModel() {

    lateinit var pitch: Pitch

    private val getAllSessionsForAPitchUseCase by lazy {
        ServiceProvider.getAllSessionsForAPitchUseCase
    }

    fun getAllSessions(): MutableLiveData<ArrayList<Session>>{
        val sessionsMutableData = MutableLiveData<ArrayList<Session>>()
        getAllSessionsForAPitchUseCase.execute(pitch).subscribe{
            sessionsMutableData.value = it as ArrayList<Session>
        }
        return sessionsMutableData
    }

}
