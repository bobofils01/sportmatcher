package com.example.sportmatcher.viewModels.sports

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.viewModels.AbstractViewModel
import io.reactivex.disposables.CompositeDisposable

class AddPitchViewModel: AbstractViewModel() {

    val address by lazy { MutableLiveData<String>() }
    val namePitch by lazy { MutableLiveData<String>() }
    val description by lazy { MutableLiveData<String>() }
    val latitude by lazy { MutableLiveData<Double>() }
    val longitude by lazy { MutableLiveData<Double>() }
    val sport by lazy { MutableLiveData<String>() }

    val photo by lazy { MutableLiveData<String>() }

    private val addPitchUseCase by lazy {
        ServiceProvider.addPitchUseCase
    }

    fun onAddPitchClicked(){
        Log.d("ADDPITCH", address.value + " "+ latitude.value+ " "+longitude.value)
        val newPitch = Pitch("", namePitch.value, description.value ,"" ,address.value, sport.value, latitude.value, longitude.value)
        addPitchUseCase.execute(newPitch).subscribe()
    }
}