package com.example.sportmatcher.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sportmatcher.ui.SettingsFragment
import com.example.sportmatcher.ui.SportChoiceFragment
import com.example.sportmatcher.ui.friendship.FriendsFragment
import com.example.sportmatcher.ui.preferences.PreferencesFragment

class NavigationPageAdapter(fm: FragmentManager, private var tabCount: Int) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int { return tabCount }

    override fun getItem(position: Int): Fragment {

        Log.d("Roman NavigationPageAda", position.toString())
        return when (position){
            0 -> {
                SportChoiceFragment()
            }
            1 -> {
                PreferencesFragment()
            }
            2 -> {
                FriendsFragment()
            }
            else -> {
                SettingsFragment()
            }
        }
    }
}