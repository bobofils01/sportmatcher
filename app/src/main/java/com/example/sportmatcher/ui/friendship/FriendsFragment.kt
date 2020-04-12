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
import kotlinx.android.synthetic.main.friends_view_layout.*

class FriendsFragment: Fragment() {

    private val friendsViewModel : FriendsViewModel by lazy {
        ViewModelProvider(this).get(FriendsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.friends_layout, container, false)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = FriendsPageAdapter(
            childFragmentManager,
            2
        )

        val viewPage: ViewPager = containerFriendsViewPage
        viewPage.adapter  = fragment

        tabsFriendsPage.setupWithViewPager(viewPage)
        tabsFriendsPage.getTabAt(0)?.setIcon(R.drawable.friends_list)
        tabsFriendsPage.getTabAt(1)?.setIcon(R.drawable.add_friend)

    }

}