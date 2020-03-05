package com.example.sportmatcher.viewModels.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.map.MapInfo
import io.reactivex.disposables.CompositeDisposable

class MapViewModel: ViewModel(){
    var addressSearched = MutableLiveData<String>()

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val mapUseCase by lazy{
        null//ServiceProvider.mapUseCase
    }
    /*
    fun onSearchAddressClicked() {
        compositeDisposable.add(
            mapUseCase.execute(
                MapInfo(
                    addressSearched.value
                )
            ).subscribe()
        )

    }*/
}