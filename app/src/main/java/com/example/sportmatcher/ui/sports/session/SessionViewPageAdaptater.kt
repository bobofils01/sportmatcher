package com.example.sportmatcher.ui.sports.session

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.ui.SettingsFragment
import com.example.sportmatcher.ui.SportChoiceFragment
import com.example.sportmatcher.ui.preferences.PreferencesFragment

class SessionViewPageAdaptater(fm: FragmentManager, private var tabCount: Int, private var session : Session) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int { return tabCount }

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> { ShowSessionFragment.newInstance(session) }
            else -> { SessionChatFragment.newInstance(session) }
        }
    }
}