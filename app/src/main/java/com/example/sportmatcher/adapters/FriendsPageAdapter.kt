package com.example.sportmatcher.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sportmatcher.ui.friendship.AllUsersFragment
import com.example.sportmatcher.ui.friendship.FriendsViewFragment

class FriendsPageAdapter (fm: FragmentManager, private var tabCount: Int)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FriendsViewFragment()
            else -> AllUsersFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}