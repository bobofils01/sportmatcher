package com.example.sportmatcher.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.sportmatcher.R
import com.example.sportmatcher.domain.friendship.AddFriendUseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.viewModels.friendship.FriendsViewModel
import kotlinx.android.synthetic.main.user_item.view.*

class UsersListAdapter (friends: ArrayList<User>, ctx: Context, private val callbackAddFriend: (User)->Unit):
    ArrayAdapter<User>(ctx, R.layout.user_item, friends) {

    private class UserItemViewHolder {
        internal var email: TextView? = null
        internal var addFriendBtn: Button? = null
    }

    override fun getView(i: Int, viewParam: View?, viewGroup: ViewGroup): View {
        var view = viewParam
        val viewHolder: UserItemViewHolder

        lateinit var viewNotNull: View
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.user_item, viewGroup, false)
            viewNotNull = view
            viewHolder = UserItemViewHolder()
            viewHolder.email = view.email_user as TextView
            viewHolder.addFriendBtn = view.addFriendBtn as Button

        } else {
            viewHolder = view.tag as UserItemViewHolder
            viewNotNull = view
        }

        val userItem = getItem(i)
        viewHolder.email!!.text = userItem!!.email
        viewNotNull.tag = viewHolder

        viewHolder.addFriendBtn!!.setOnClickListener{callbackAddFriend(userItem)}
        return viewNotNull
    }
}