package com.example.sportmatcher.ui.sports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.viewModels.sports.AllSessionsOfAPitchViewModel
import com.example.sportmatcher.viewModels.sports.AllSportsViewModel

class AllSessionOfAPtichActivity: AppCompatActivity() {
    companion object {

        private const val PITCH_KEY = "pitch_key"

        fun getIntent(context: Context, pitch: Pitch): Intent {
            return Intent(context, AllSessionOfAPtichActivity::class.java).apply {
                putExtra(PITCH_KEY, pitch)
            }
        }
    }

    private val allSessionsOfAPitchViewModel : AllSessionsOfAPitchViewModel by lazy {
        ViewModelProvider(this).get(AllSessionsOfAPitchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_sessions_of_a_pitch_layout)
        allSessionsOfAPitchViewModel.pitch = intent.extras?.get(PITCH_KEY) as Pitch

    }
}