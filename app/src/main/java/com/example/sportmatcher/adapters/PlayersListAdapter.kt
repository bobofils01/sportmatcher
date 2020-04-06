package com.example.sportmatcher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportmatcher.R
import com.example.sportmatcher.model.User
import kotlinx.android.synthetic.main.friend_item.view.*

class PlayersListAdapter(private val players: ArrayList<User>, private val callbackDeletion : (User)->Unit) : RecyclerView.Adapter<PlayersListAdapter.PlayerItemViewHolder>()  {

    class PlayerItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        internal var idFriend: TextView? = null
        internal var emailFriend: TextView? = null
        internal var firstNameFriend: TextView? = null
        internal var lastNameFriend: TextView? = null
        internal var deletionBtn: Button? = null

        fun bind(user: User, callbackDeletion : (User)->Unit) {
            emailFriend!!.text = user.email
            deletionBtn!!.setOnClickListener{callbackDeletion(user)}
        }

        init {
            emailFriend = view.emailFriend as TextView
            firstNameFriend = view.firstNameFriend as TextView
            lastNameFriend = view.lastNameFriend as TextView
            deletionBtn = view.deleteFriendBtn as Button
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.friend_item, parent, false)
        return PlayerItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerItemViewHolder, position: Int) {

    }
}