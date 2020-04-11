package com.example.sportmatcher.ui.sports.session
import android.content.Context
import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.viewpager.widget.ViewPager
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.SessionViewPageAdapter
import com.example.sportmatcher.model.sport.Session

import kotlinx.android.synthetic.main.activity_session_view.*


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

        tabs_sessions_page.getTabAt(0)?.text = "Session"
        tabs_sessions_page.getTabAt(1)?.text = "Chat"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}