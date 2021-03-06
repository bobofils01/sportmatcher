package com.example.sportmatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sportmatcher.R
import com.example.sportmatcher.model.sport.Session

class SessionListAdapter(sessions: ArrayList<Session>, ctx : Context) :
    ArrayAdapter<Session>(ctx, R.layout.session_item, sessions){

    private class SessionItemViewHolder {
        internal var totalNbPlayers: TextView? = null
        internal var nbPlayersSigned: TextView? = null
        internal var price: TextView? = null
        internal var address: TextView? = null
    }

    override fun getView(i: Int, viewParam: View?, viewGroup: ViewGroup): View {
        var view = viewParam
        val viewHolder: SessionItemViewHolder

        lateinit var viewNotNull:View
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.session_item, viewGroup, false)
            viewNotNull = view
            viewHolder = SessionItemViewHolder()
            viewHolder.totalNbPlayers = view.findViewById<View>(R.id.sessionTotalNbPlayers) as TextView
            viewHolder.nbPlayersSigned = view.findViewById<View>(R.id.sessionNbPlayersSigned) as TextView
            //viewHolder.address = view.findViewById<View>(R.id.pitchAddress) as TextView
            viewHolder.price = view.findViewById<View>(R.id.sessionPrice) as TextView
        } else{
            viewHolder = view.tag as SessionItemViewHolder
            viewNotNull = view
        }

        val sessionItem = getItem(i)
        viewHolder.totalNbPlayers!!.text = sessionItem!!.totalNbPlayers.toString() + " players"
        viewHolder.nbPlayersSigned!!.text = sessionItem!!.nbPlayersSigned.toString() + " players already signed"
        //viewHolder.address!!.text = sessionItem!!.address
        viewHolder.price!!.text = sessionItem!!.price.toString() + " €"
        viewNotNull.tag = viewHolder

        return viewNotNull
    }
}
