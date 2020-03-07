package com.example.sportmatcher.ui.sports
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.PitchesListAdapter
import com.example.sportmatcher.viewModels.sports.AllSportsViewModel
import kotlinx.android.synthetic.main.all_sports_view_layout.*

class AllSportsViewFragment: Fragment(){

    private val allSportsViewModel : AllSportsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AllSportsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.all_sports_view_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO get The map and manipulate it
        //TODO use ListAdapter to show list in other layout.
        //TODO add also item touchhelper listener

        val listView : ListView = sports_list as ListView

        allSportsViewModel.getAllSports().observe(requireActivity(), Observer{sports ->
            val adapter = PitchesListAdapter(sports, requireContext())
            listView.adapter =  adapter
        })
    }


}