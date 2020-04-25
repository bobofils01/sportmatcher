package com.example.sportmatcher.ui.sports.session
import android.content.Context
import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.SessionViewPageAdapter
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.ui.NavigationActivity

import kotlinx.android.synthetic.main.activity_session_view.*
import kotlinx.android.synthetic.main.navigation_bar_layout.*
import kotlinx.android.synthetic.main.show_session_fragment.session_title

class SessionViewActivity : AppCompatActivity() {

    companion object {

        private const val SESSION_KEY = "session_key"

        fun getIntent(context: Context, session: Session): Intent {

            return Intent(context, SessionViewActivity::class.java).apply {
                putExtra(SESSION_KEY, session)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_view)

        //Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val currentSession = intent.extras?.get(SESSION_KEY) as Session

        val fragment =
            SessionViewPageAdapter(
                supportFragmentManager,
                2,
                currentSession
            )
        val viewPage: ViewPager = findViewById(R.id.containerSessionViewPage)
        viewPage.adapter  = fragment
        tabs_sessions_page.setupWithViewPager(viewPage)

        tabs_sessions_page.getTabAt(0)?.setIcon(R.drawable.american_football_ball)
        tabs_sessions_page.getTabAt(1)?.setIcon(R.drawable.chat)
    }

    //Si on appuie sur le boutton back du toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

