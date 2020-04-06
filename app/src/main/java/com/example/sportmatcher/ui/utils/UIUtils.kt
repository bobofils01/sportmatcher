package com.example.sportmatcher.ui.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData

object UIUtils {

    fun addOnTextViewChange(view: TextView, valueTochange : MutableLiveData<String>){

        view.addTextChangedListener (object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrBlank()) {
                    valueTochange.value = p0.toString()
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

    }

}