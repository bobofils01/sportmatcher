package com.example.sportmatcher.viewModels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class AbstractViewModel: ViewModel() {


    protected val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable.apply {
            dispose()
            clear()
        }
        super.onCleared()
    }
}