package com.example.sportmatcher.ui.friendship

import android.content.Context
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
import com.example.sportmatcher.adapters.UsersListAdapter
import com.example.sportmatcher.di.ServiceProvider.getAuthenticatedUserUseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.viewModels.friendship.FriendsViewModel
import kotlinx.android.synthetic.main.friends_layout.*

class FriendsFragment: Fragment() {

    private val friendsViewModel : FriendsViewModel by lazy {
        ViewModelProvider(this).get(FriendsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.friends_layout, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val from = getAuthenticatedUserUseCase.execute()
        Log.d("Roman", "attach "+from?.toMap())
        if (from != null) {
            friendsViewModel.setUser(from!!)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersListView = all_users_list as ListView

        val friendsListView = friends_list as ListView

        friendsViewModel.getAllFriends().observe(viewLifecycleOwner, Observer { friends ->
            val adapter = FriendListAdapter(friends, context!!) { userToDelete ->
                friendsViewModel.deleteFriend(user = userToDelete)
            }
            friendsListView.adapter = adapter
        })


        friendsViewModel.getAllUsers().observe(viewLifecycleOwner, Observer {users ->
            val adapter = UsersListAdapter(users, context!!){userToAdd ->
                friendsViewModel.addFriend(userToAdd)
            }
            usersListView.adapter = adapter
        })
    }
}