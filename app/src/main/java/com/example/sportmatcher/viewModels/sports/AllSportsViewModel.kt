package com.example.sportmatcher.viewModels.sports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.viewModels.AbstractViewModel


class AllSportsViewModel : AbstractViewModel(){

    private val getPitchesForUseCase by lazy {
        ServiceProvider.getPitchesForUseCase
    }

    private val addPitchClicked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getViewAddPitchClickedLiveData(): LiveData<Boolean> = addPitchClicked

    fun onAddPitchClicked(){
        addPitchClicked.value = true
    }

    fun getAllSports( sportName : String) : MutableLiveData<ArrayList<Pitch>>{

        val sportsMutableData = MutableLiveData<ArrayList<Pitch>>()
        compositeDisposable.add(
            getPitchesForUseCase.execute(sportName).subscribe{
                sportsMutableData.value = it as ArrayList<Pitch>
            }
        )

        return sportsMutableData
    }

    fun goBackSportHomepage() {
        addPitchClicked.value = false
    }
}