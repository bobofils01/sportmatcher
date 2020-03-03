package com.example.sportmatcher.ui.map
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportmatcher.R

class AllSportsViewFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.all_sports_view_layout, container, false)
        //TODO use ListAdapter to show list in other layout.
        return view
    }
}