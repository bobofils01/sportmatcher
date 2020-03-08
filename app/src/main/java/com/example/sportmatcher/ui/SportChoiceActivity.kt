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

class SportChoiceActivity : AppCompatActivity(){//, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sport_choice_layout)
    }

    fun onSportClick(v: View) {
        val b = v as Button
        val sportName = b.text.toString()
        val intent = Intent(this, Sport::class.java).apply {
            putExtra("SPORT_NAME", sportName)
        }
        startActivity(intent)
    }
}
