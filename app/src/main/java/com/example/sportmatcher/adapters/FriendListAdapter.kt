package com.example.sportmatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.sportmatcher.R
import com.example.sportmatcher.model.User
import com.example.sportmatcher.viewModels.friendship.FriendsViewModel
import kotlinx.android.synthetic.main.friend_item.view.*

class FriendListAdapter(friends: ArrayList<User>, ctx: Context, private val callbackDeletion : (User)->Unit):
    ArrayAdapter<User>(ctx, R.layout.friend_item, friends) {

    private class FriendItemViewHolder {
        internal var idFriend: TextView? = null
        internal var emailFriend: TextView? = null
        internal var firstNameFriend: TextView? = null
        internal var lastNameFriend: TextView? = null
        internal var deletionBtn: Button? = null
    }

    override fun getView(i: Int, viewParam: View?, viewGroup: ViewGroup): View {
        var view = viewParam
        val viewHolder: FriendItemViewHolder

        lateinit var viewNotNull:View
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.friend_item, viewGroup, false)
            viewNotNull = view
            viewHolder = FriendItemViewHolder()
            viewHolder.emailFriend = view.emailFriend as TextView
            viewHolder.firstNameFriend = view.firstNameFriend as TextView
            viewHolder.lastNameFriend = view.lastNameFriend as TextView
            viewHolder.deletionBtn = view.deleteFriendBtn as Button
        } else{
            viewHolder = view.tag as FriendItemViewHolder
            viewNotNull = view
        }

        val friendItem = getItem(i)
        viewHolder.emailFriend!!.text = friendItem!!.email
        viewHolder.firstNameFriend!!.text = friendItem!!.firstName
        viewHolder.lastNameFriend!!.text = friendItem!!.lastName
        viewNotNull.tag = viewHolder

        viewHolder.deletionBtn!!.setOnClickListener{callbackDeletion(friendItem)}
        /*
        viewHolder.deletionBtn!!.setOnClickListener{
            friendsViewModel.deleteFriend(friendItem)
        }
        */
        return viewNotNull
    }
}
