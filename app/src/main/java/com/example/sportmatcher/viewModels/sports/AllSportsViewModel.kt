package com.example.sportmatcher.viewModels.sports

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class AllSportsViewModel : ViewModel(){


    fun getAllSports() : MutableLiveData<List<String>>{
        //TODO get it from the usecase call
        val sportsMutableData = MutableLiveData<List<String>>()

        val sportList: MutableList<String> = ArrayList()
        sportList.add("blocry")
        sportList.add("mounier")
        sportList.add("parc madou")
        sportList.add("sainte Barbe")
        sportList.add("Basic fit")

        sportsMutableData.value = sportList

        return sportsMutableData
    }
}