package com.example.sportmatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportmatcher.R
import com.example.sportmatcher.model.sport.Pitch

class PitchesListAdapter(sportsList : ArrayList<Pitch>, ctx : Context)
    : ArrayAdapter<Pitch>(ctx, R.layout.sport_item, sportsList){

    private class SportItemViewHolder {
        internal var pitchPicture: ImageView? = null
        internal var name: TextView? = null
        internal var adress: TextView? = null
        internal var description: TextView? = null
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
            viewHolder.adress = view.findViewById<View>(R.id.pitchAdress) as TextView
            viewHolder.pitchPicture = view.findViewById<View>(R.id.pitchPicture) as ImageView
        } else{
            viewHolder = view.tag as SportItemViewHolder
        }

        val sportItem = getItem(i)
        viewHolder.name!!.text = sportItem!!.name
        viewHolder.description!!.text = sportItem!!.description
        viewHolder.adress!!.text = sportItem!!.address
        viewHolder.pitchPicture!!.setImageResource(R.mipmap.ic_launcher_round)
        view.tag = viewHolder
        return view
    }

}
