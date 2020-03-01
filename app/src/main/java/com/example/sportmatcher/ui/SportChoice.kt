package com.example.sportmatcher.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.sportmatcher.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.sport_choice_layout.*

class SportChoice : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sport_choice_layout)

        val basketball: Button = findViewById(R.id.basketball)
        val hockey: Button = findViewById(R.id.hockey)
        val soccer: Button = findViewById(R.id.soccer)
        val futsal: Button = findViewById(R.id.futsal)
        val handball: Button = findViewById(R.id.handball)
        val voleyball: Button = findViewById(R.id.voleyball)
        val tennis: Button = findViewById(R.id.tennis)
        val badminton: Button = findViewById(R.id.badminton)
        val squash: Button = findViewById(R.id.squash)
        val pingpong: Button = findViewById(R.id.ping_pong)


        basketball.setOnClickListener(this)
        hockey.setOnClickListener(this)
        soccer.setOnClickListener(this)
        futsal.setOnClickListener(this)
        handball.setOnClickListener(this)
        voleyball.setOnClickListener(this)
        tennis.setOnClickListener(this)
        badminton.setOnClickListener(this)
        squash.setOnClickListener(this)
        pingpong.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val b = v as Button
        val sportName = b.text.toString()
        val intent = Intent(this, Sport::class.java).apply {
            putExtra("SPORT_NAME", sportName)
        }
        startActivity(intent)
    }
}
