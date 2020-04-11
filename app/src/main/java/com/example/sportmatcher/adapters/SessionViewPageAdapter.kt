package com.example.sportmatcher.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.ui.sports.session.SessionChatFragment
import com.example.sportmatcher.ui.sports.session.ShowSessionFragment

class SessionViewPageAdapter(fm: FragmentManager, private var tabCount: Int, private var session : Session) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int { return tabCount }

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> {
                ShowSessionFragment.newInstance(
                    session
                )
            }
            else -> {
                SessionChatFragment.newInstance(
                    session
                )
            }
        }
    }
}