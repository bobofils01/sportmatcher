package com.example.sportmatcher.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportmatcher.R
import com.example.sportmatcher.model.User
import kotlinx.android.synthetic.main.friend_item.view.*

class PlayersListAdapter(private val players: ArrayList<User>,private val currentUser: User, private val callbackDeletion : (User)->Unit) : RecyclerView.Adapter<PlayersListAdapter.PlayerItemViewHolder>()  {

    class PlayerItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        internal var idFriend: TextView? = null
        internal var emailFriend: TextView? = null
        internal var firstNameFriend: TextView? = null
        internal var lastNameFriend: TextView? = null
        internal var deletionBtn: ImageButton? = null

        fun bind(user: User, currentUser: User,callbackDeletion : (User)->Unit) {
            emailFriend!!.text = user.email
            if(currentUser == user) {
                deletionBtn!!.isClickable = false
                deletionBtn!!.visibility = View.INVISIBLE
                //deletionBtn!!.text = "Me"

            }else {
                deletionBtn!!.setBackgroundColor(Color.RED)
                //deletionBtn!!.text ="X"
                deletionBtn!!.setOnClickListener { callbackDeletion(user) }
            }
        }

        init {
            emailFriend = view.emailFriend as TextView
            firstNameFriend = view.firstNameFriend as TextView
            lastNameFriend = view.lastNameFriend as TextView
            deletionBtn = view.deleteFriendBtn as ImageButton
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
        val user = players.get(position)
        holder.bind(user, currentUser, callbackDeletion)
    }
}