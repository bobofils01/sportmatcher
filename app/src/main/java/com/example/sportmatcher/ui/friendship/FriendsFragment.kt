package com.example.sportmatcher.ui.friendship

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.FriendsPageAdapter
import com.example.sportmatcher.viewModels.friendship.FriendsViewModel
import kotlinx.android.synthetic.main.friends_layout.*

class FriendsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.friends_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = FriendsPageAdapter(
            childFragmentManager,
            2
        )

        val viewPage: ViewPager = containerFriendsViewPage
        viewPage.adapter  = fragment

        //Bar de navigation de l'onglet amis
        tabsFriendsPage.setupWithViewPager(viewPage)
        tabsFriendsPage.getTabAt(0)?.setIcon(R.drawable.friends_list)
        tabsFriendsPage.getTabAt(1)?.setIcon(R.drawable.add_friend)

    }

}