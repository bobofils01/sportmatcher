package com.example.sportmatcher.viewModels.sports

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch


class AllSportsViewModel : ViewModel(){

    private val getAllPitchesUseCase by lazy {
        ServiceProvider.getAllPitchesUseCase
    }

    fun getAllSports() : MutableLiveData<ArrayList<Pitch>>{

        val sportsMutableData = MutableLiveData<ArrayList<Pitch>>()
        //TODO get it from the usecase call and observe the change.

        val sportList: MutableList<Pitch> = ArrayList()

        getAllPitchesUseCase.execute().subscribe{
            sportsMutableData.value = it as ArrayList<Pitch>
        }
        /*sportList.add(Pitch(uid = "", name = "Blocry", description = "le bon bail de louvain", pitchPicture = "pas là", address = "rue de la blocry 1"))
        sportList.add(Pitch(uid = "", name = "Basic fit", description = "le bon bail de louvain", pitchPicture = "pas là", address = "rue de la blocry 1"))
        sportList.add(Pitch(uid = "", name = "Namen", description = "le bon bail de louvain", pitchPicture = "pas là", address = "rue de la blocry 1"))
        sportList.add(Pitch(uid = "", name = "Alma", description = "le bon bail de louvain", pitchPicture = "pas là", address = "rue de la blocry 1"))
        sportList.add(Pitch(uid = "", name = "KOTSport", description = "le bon bail de louvain", pitchPicture = "pas là", address = "rue de la blocry 1"))

        sportsMutableData.value = sportList as ArrayList<Pitch>
        */
        return sportsMutableData
    }
}