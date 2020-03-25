package com.example.sportmatcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.sportmatcher.R

class SportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_sports_empty_view_layout)

        //Toast.makeText(this, "This world will end", Toast.LENGTH_LONG).show()

        val name = intent.getStringExtra("SPORT_NAME")

        //val sportName = intent.getStringExtra(sport)
        val sportName = findViewById<TextView>(R.id.sport).apply {
            text = name
        }
    }
}
