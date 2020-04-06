package com.example.sportmatcher.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sportmatcher.R
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.ChatMessage

class SessionChatListAdapter constructor(sessions: ArrayList<ChatMessage>, ctx : Context, activity: Activity)
    : ArrayAdapter<ChatMessage>(ctx, R.layout.chat_to_item, sessions){

    private class ChatItemViewHolder {
        internal var sender: TextView? = null
        internal var message: TextView? = null
    }

    private var currentUserUUID:String? = null

    override fun getView(i: Int, viewParam: View?, viewGroup: ViewGroup): View {
        var viewReturn = viewParam
        val viewHolder: ChatItemViewHolder

        val chatItem = getItem(i)!!
        val isMyMesage = chatItem.senderUUID == currentUserUUID!!

        if (viewReturn == null) {
            //bind the view to the layout
            val inflater = LayoutInflater.from(context)
            if(isMyMesage){
                viewReturn = inflater.inflate(R.layout.chat_to_item, viewGroup, false)
            }else{
                viewReturn = inflater.inflate(R.layout.chat_from_item, viewGroup, false)
            }

            viewHolder = ChatItemViewHolder()
            viewHolder.sender = viewReturn.findViewById<View>(R.id.fromTextViewId) as TextView
            viewHolder.message = viewReturn.findViewById<View>(R.id.msgTextViewID) as TextView

        }else{
            viewHolder = viewReturn.tag as ChatItemViewHolder
            viewReturn = viewReturn
        }

        viewHolder.message!!.text = chatItem.message
        if(isMyMesage)
            viewHolder.sender!!.text = "Me"
        else
            viewHolder.sender!!.text = "From"

        viewReturn!!.tag = viewHolder

        return viewReturn
    }


    init {
        val user = ServiceProvider.getAuthenticatedUserUseCase.execute()
        this.currentUserUUID = user?.uid
    }


}
