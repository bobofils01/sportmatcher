package com.example.sportmatcher.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportmatcher.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TO DO if already logged on start another activity.
        setContentView(R.layout.activity_main)
    }
}
