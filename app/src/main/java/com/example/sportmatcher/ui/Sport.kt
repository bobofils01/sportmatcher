package com.example.sportmatcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.sportmatcher.R

class Sport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_sports_empty_view_layout)

        val name = intent.getStringExtra("SPORT_NAME")

        //val sportName = intent.getStringExtra(sport)
        val sportName = findViewById<TextView>(R.id.sport).apply {
            text = name
        }
    }
}
