package com.example.sportmatcher.ui.sports

import android.os.Bundle
import com.example.sportmatcher.R
import androidx.appcompat.app.AppCompatActivity

class SportHomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport_homepage)

        val sportName = intent.getStringExtra("SPORT_NAME")

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.sportHomePageFragment,
                AllSportsViewFragment(),
                "ALL SPORTS"
            ).commit()

    }
}