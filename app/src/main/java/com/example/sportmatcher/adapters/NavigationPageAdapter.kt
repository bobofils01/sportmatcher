package com.example.sportmatcher.adapters

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

        return when (position){
            0 -> {
                SportChoiceFragment() //Lance la liste des sports à l'appuie du boutton d'indice 0 dans la bar de navigation
            }
            1 -> {
                PreferencesFragment() //Lance la gestion des préférences à l'appuie du boutton d'indice 1 dans la bar de navigation
            }
            2 -> {
                FriendsFragment() //Lance la liste d'amis à l'appuie du boutton d'indice 2 dans la bar de navigation
            }
            else -> {
                SettingsFragment() //Lance les paramètres à l'appuie du boutton d'indice 3 dans la bar de navigation
            }
        }
    }
}