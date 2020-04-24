package com.example.sportmatcher.ui.sports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.SessionListAdapter
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.viewModels.sports.AllSessionsOfAPitchViewModel
import kotlinx.android.synthetic.main.all_sessions_of_a_pitch_layout.*


class AllSessionOfAPitchActivity: AppCompatActivity() {
    companion object {

        private const val PITCH_KEY = "pitch_key"

        fun getIntent(context: Context, pitch: Pitch): Intent {
            return Intent(context, AllSessionOfAPitchActivity::class.java).apply {
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
        val listView : ListView = listViewAllSessions as ListView

        initTextViews()
        pitch_name.text = allSessionsOfAPitchViewModel.pitch.name

        allSessionsOfAPitchViewModel.getAllSessions().observe(this, Observer { sessions ->
            val adapter = SessionListAdapter(sessions, this)
            listView.adapter = adapter
        })
    }

    private fun initTextViews(){
        pitch_name.text = allSessionsOfAPitchViewModel.pitch.name
        pitch_address.text = allSessionsOfAPitchViewModel.pitch.address

        new_session.setOnClickListener {
            startActivity(AddSessionToPitchActivity.getIntent(this, allSessionsOfAPitchViewModel.pitch))
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, SportHomePageActivity::class.java).apply {
            putExtra("SPORT_NAME", allSessionsOfAPitchViewModel.pitch.sport)
        }
        startActivity(intent)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}