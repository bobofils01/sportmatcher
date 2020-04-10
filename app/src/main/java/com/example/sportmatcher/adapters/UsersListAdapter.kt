package com.example.sportmatcher.adapters

import android.content.Context
import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sportmatcher.R
import com.example.sportmatcher.domain.friendship.AddFriendUseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.viewModels.friendship.FriendsViewModel
import kotlinx.android.synthetic.main.user_item.view.*

class UsersListAdapter (private var userList: ArrayList<User>, ctx: Context, private val callbackAddFriend: (User)->Unit):
    ArrayAdapter<User>(ctx, R.layout.user_item, userList), Filterable {

    private val originalFriends = userList.clone() as ArrayList<User>//ArrayList<User>().addAll(userList)
    private var userFilter: Filter? = null

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

    override fun getFilter(): Filter {
        if (userFilter == null) userFilter = UserFilter()
        return userFilter!!
    }

    private inner class UserFilter: Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            // No filter implemented we return all the list
            if (constraint.isNullOrBlank()) {
                Log.d("Roman gros?", originalFriends.size.toString())
                results.values = originalFriends
                results.count = originalFriends.size
            } else { // We perform filtering operation
                val nUserList = ArrayList<User>()
                for (p in originalFriends.listIterator()) {
                    Log.d("Roman for", p.email.toString())
                    if (p.email.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        nUserList.add(p)
                    }
                }
                Log.d("Roman", nUserList.size.toString() + nUserList.toString())
                results.values = nUserList
                results.count = nUserList.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated()
            else {
                userList.clear()
                userList.addAll(results.values as ArrayList<User>)
                Log.d("Roman publish", userList.size.toString())
                notifyDataSetChanged()
            }
        }
    }
}