package com.example.sportmatcher.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sportmatcher.R
import kotlinx.android.synthetic.main.session_item.view.*
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.ui.sports.session.SessionViewActivity

class SessionListAdapter(sessions: ArrayList<Session>, ctx : Context) :
    ArrayAdapter<Session>(ctx, R.layout.session_item, sessions){

    private class SessionItemViewHolder {
        internal var title: TextView? = null
        internal var description: TextView? = null
        internal var date: TextView? = null
        internal var time: TextView? = null
        internal var createdBy: TextView? = null
    }

    override fun getView(i: Int, viewParam: View?, viewGroup: ViewGroup): View {
        var view = viewParam
        val viewHolder: SessionItemViewHolder

        lateinit var viewNotNull:View
        if (view == null) {
            //bind the view to the layout
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.session_item, viewGroup, false)
            viewNotNull = view
            viewHolder = SessionItemViewHolder()
            viewHolder.title = view.session_title
            viewHolder.description = view.session_description
            viewHolder.date = view.session_date
            viewHolder.time = view.session_time
            viewHolder.createdBy = view.session_createdby
        } else{
            viewHolder = view.tag as SessionItemViewHolder
            viewNotNull = view
        }

        val sessionItem = getItem(i)
        //changevalues
        viewHolder.title!!.text = sessionItem!!.title
        viewHolder.description!!.text = sessionItem!!.description
        viewHolder.date!!.text = sessionItem!!.date
        viewHolder.time!!.text = sessionItem!!.time
        viewHolder.createdBy!!.text = "created by : ${sessionItem!!.createdBy}"
        viewNotNull.tag = viewHolder
        viewNotNull.setOnClickListener( object: View.OnClickListener {
            override fun onClick(p0: View?) {
                context.startActivity(
                    SessionViewActivity.getIntent(
                        viewNotNull.context,
                        sessionItem
                    )
                )
            }
        })

        return viewNotNull
    }
}
