package com.example.sportmatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.sportmatcher.R
import com.example.sportmatcher.model.User
import kotlinx.android.synthetic.main.friend_item.view.*

class FriendListAdapter(friends: ArrayList<User>, ctx: Context, private val callbackDeletion : (User)->Unit):
    ArrayAdapter<User>(ctx, R.layout.friend_item, friends) {

    private class FriendItemViewHolder {
        internal var idFriend: TextView? = null
        internal var displayedName: TextView? = null
        internal var firstNameFriend: TextView? = null
        internal var lastNameFriend: TextView? = null
        internal var deletionBtn: ImageButton? = null
    }

    override fun getView(i: Int, viewParam: View?, viewGroup: ViewGroup): View {
        var view = viewParam
        val viewHolder: FriendItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.friend_item, viewGroup, false)
            viewHolder = FriendItemViewHolder()
            viewHolder.displayedName = view.displayedName as TextView
            viewHolder.firstNameFriend = view.firstNameFriend as TextView
            viewHolder.lastNameFriend = view.lastNameFriend as TextView
            viewHolder.deletionBtn = view.deleteFriendBtn as ImageButton
        } else{
            viewHolder = view.tag as FriendItemViewHolder
        }

        val friendItem = getItem(i)

        val nameDisplayed = friendItem!!.firstName + " "+ friendItem.lastName!![0].toUpperCase() + (if(friendItem.lastName!!.length > 0) "." else "")

        viewHolder.displayedName!!.text = nameDisplayed
        //viewHolder.firstNameFriend!!.text = friendItem!!.firstName
        //viewHolder.lastNameFriend!!.text = friendItem!!.lastName
        view!!.tag = viewHolder

        viewHolder.deletionBtn!!.setOnClickListener{callbackDeletion(friendItem)}

        return view
    }
}
