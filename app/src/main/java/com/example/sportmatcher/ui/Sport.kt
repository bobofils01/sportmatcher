package com.example.sportmatcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.TextView
import com.example.sportmatcher.R
import kotlinx.android.synthetic.main.sport_layout.*

class Sport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sport_layout)

        val name = intent.getStringExtra("SPORT_NAME")

        //val sportName = intent.getStringExtra(sport)
        val sportName = findViewById<TextView>(R.id.sport).apply {
            text = name
        }
    }
}
