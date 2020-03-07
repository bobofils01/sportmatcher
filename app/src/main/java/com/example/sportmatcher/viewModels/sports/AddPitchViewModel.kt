package com.example.sportmatcher.viewModels.sports

import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch
import io.reactivex.disposables.CompositeDisposable

class AddPitchViewModel: ViewModel() {

    val address by lazy { MutableLiveData<String>() }
    val latitude by lazy { MutableLiveData<Float>() }
    val longitude by lazy { MutableLiveData<Double>() }

    val photo by lazy { MutableLiveData<String>() }

    private val addPitchUseCase by lazy {
        ServiceProvider.addPitchUseCase
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun onAddPitchClicked(){
        Log.d("ADDPITCH", address.value + " "+ latitude.value+ " "+longitude.value)
        val newPitch = Pitch("", "terrain test", "terrain de zab","",address.value, "football", 0.0, 0.1)
        addPitchUseCase.execute(newPitch).subscribe()
    }

    override fun onCleared() {
        compositeDisposable.apply {
            dispose()
            clear()
        }
        super.onCleared()
    }
}