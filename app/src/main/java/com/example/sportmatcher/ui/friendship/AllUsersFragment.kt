package com.example.sportmatcher.ui.friendship

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.UsersListAdapter
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.viewModels.friendship.FriendsViewModel
import kotlinx.android.synthetic.main.friends_users_layout.*

class AllUsersFragment: Fragment() {

    private val friendsViewModel : FriendsViewModel by lazy {
        ViewModelProvider(this).get(FriendsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.friends_users_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val from = ServiceProvider.getAuthenticatedUserUseCase.execute()
        if (from != null) {
            friendsViewModel.setUser(from)
        }

        friendsViewModel.getAllFriends().observe(viewLifecycleOwner , Observer {})

        val usersListView = all_users_list as ListView

        friendsViewModel.getAllUsers().observe(viewLifecycleOwner, Observer {users ->
            val adapter = UsersListAdapter(users, context!!){userToAdd ->
                friendsViewModel.addFriend(userToAdd)
            }

            usersListView.adapter = adapter

            editTxtFilterAllUsers.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s.toString())
                }

                override fun afterTextChanged(s: Editable) {}
            })

        })

    }

}