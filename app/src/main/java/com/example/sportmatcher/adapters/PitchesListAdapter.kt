package com.example.sportmatcher.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sportmatcher.R
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.ui.sports.AddSessionToPitchActivity
import com.example.sportmatcher.ui.sports.AllSessionOfAPitchActivity
import kotlinx.android.synthetic.main.sport_item.view.*

class PitchesListAdapter(sportsList : ArrayList<Pitch>, ctx : Context)
    : ArrayAdapter<Pitch>(ctx, R.layout.sport_item, sportsList){

    private class SportItemViewHolder {
        internal var pitchPicture: ImageView? = null
        internal var name: TextView? = null
        internal var address: TextView? = null
        internal var description: TextView? = null
        internal var addSessionToChangeActivityBtn: Button? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: SportItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.sport_item, viewGroup, false)

            viewHolder = SportItemViewHolder()
            viewHolder.name = view!!.findViewById<View>(R.id.pitchName) as TextView
            viewHolder.description = view.findViewById<View>(R.id.pitchDescription) as TextView
            viewHolder.address = view.findViewById<View>(R.id.pitchAddress) as TextView
            viewHolder.pitchPicture = view.findViewById<View>(R.id.pitchPicture) as ImageView
            viewHolder.addSessionToChangeActivityBtn = view.addSessionToChangeActivityBtn as Button
        } else{
            viewHolder = view.tag as SportItemViewHolder
        }

        val sportItem = getItem(i)
        viewHolder.name!!.text = sportItem!!.name
        viewHolder.description!!.text = sportItem!!.description
        viewHolder.address!!.text = sportItem!!.address
        viewHolder.pitchPicture!!.setImageResource(R.mipmap.ic_launcher_round)
        view.tag = viewHolder

        viewHolder.addSessionToChangeActivityBtn!!.setOnClickListener {
            Toast.makeText(context,"On add session"+sportItem.toMap().toString(), Toast.LENGTH_LONG).show()
            context.startActivity(AddSessionToPitchActivity.getIntent(view.context, sportItem))
        }

        view.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                Log.d("SetOnClickList", sportItem.toMap().toString())
                context.startActivity(AllSessionOfAPitchActivity.getIntent(view.context, sportItem))
            }
        })
        return view
    }
}
