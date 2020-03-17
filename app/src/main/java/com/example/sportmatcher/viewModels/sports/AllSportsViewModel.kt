package com.example.sportmatcher.viewModels.sports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch


class AllSportsViewModel : ViewModel(){

    private val getPitchesForUseCase by lazy {
        ServiceProvider.getPitchesForUseCase
    }

    val sportName by lazy { MutableLiveData<String>() }

    private val addPitchClicked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getViewAddPitchClickedLiveData(): LiveData<Boolean> = addPitchClicked

    fun onAddPitchClicked(){
        addPitchClicked.value = true
    }

    fun getAllSports() : MutableLiveData<ArrayList<Pitch>>{

        val sportsMutableData = MutableLiveData<ArrayList<Pitch>>()

        getPitchesForUseCase.execute(sportName.value!!).subscribe{
            sportsMutableData.value = it as ArrayList<Pitch>
        }

        return sportsMutableData
    }

    fun reset() {
        addPitchClicked.value = false
    }

    /*override fun onCleared() {
        compositeDisposable.apply {
            dispose()
            clear()
        }
        super.onCleared()
    }

    init{
        compositeDisposable.add(
    }*/
}