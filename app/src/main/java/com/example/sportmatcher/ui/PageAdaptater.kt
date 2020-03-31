package com.example.sportmatcher.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sportmatcher.ui.preferences.PreferencesFragment

class PageAdaptater(fm: FragmentManager, private var tabCount: Int) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int { return tabCount }

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> { SportChoiceFragment() }
            1 -> { PreferencesFragment() }
            else -> { SettingsFragment() }
        }
    }
}