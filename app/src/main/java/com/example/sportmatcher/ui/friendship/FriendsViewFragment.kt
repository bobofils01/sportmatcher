package com.example.sportmatcher.ui.friendship

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.FriendListAdapter
import com.example.sportmatcher.di.ServiceProvider.getAuthenticatedUserUseCase
import com.example.sportmatcher.viewModels.friendship.FriendsViewModel
import kotlinx.android.synthetic.main.friends_view_layout.*

class FriendsViewFragment: Fragment() {

    private val friendsViewModel : FriendsViewModel by lazy {
        ViewModelProvider(this).get(FriendsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.friends_view_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val from = getAuthenticatedUserUseCase.execute()
        if (from != null) {
            friendsViewModel.setUser(from)
        }

        Log.d("Roman FriendsVF user", from?.toMap().toString())
        val friendsListView = friends_list as ListView

        friendsViewModel.getAllFriends().observe(viewLifecycleOwner, Observer { friends ->
            val adapter = FriendListAdapter(friends, context!!) { userToDelete ->
                friendsViewModel.deleteFriend(user = userToDelete)
            }
            friendsListView.adapter = adapter

        })
    }
}