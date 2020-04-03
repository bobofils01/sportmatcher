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
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.dto.friendship.InvitationDTO
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val from = activity!!.getSharedPreferences(R.string.SESSION_USER.toString(), Context.MODE_PRIVATE).getString("USER", "")
        friendsViewModel.setUser(from)

        val usersListView = all_users_list as ListView

        friendsViewModel.getAllUsers().observe(viewLifecycleOwner, Observer {users ->
            val filteredUsers = users.filter { user -> user.uid!=from } as ArrayList<User>
            val adapter = UsersListAdapter(filteredUsers, context!!){userToAdd ->
                friendsViewModel.addFriend(userToAdd)
            }
            usersListView.adapter = adapter
        })

        val friendsListView = friends_list as ListView

        friendsViewModel.getAllFriends(from!!).observe(viewLifecycleOwner, Observer { friends ->
            val adapter = FriendListAdapter(friends, context!!) { userToDelete ->
                friendsViewModel.deleteFriend(user = userToDelete)
            }
            friendsListView.adapter = adapter
        })
    }
}