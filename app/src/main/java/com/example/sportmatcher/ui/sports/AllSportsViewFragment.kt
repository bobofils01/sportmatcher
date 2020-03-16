package com.example.sportmatcher.ui.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.PitchesListAdapter
import com.example.sportmatcher.viewModels.sports.AllSportsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.all_sports_view_layout.*


class AllSportsViewFragment: Fragment(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap

    private val allSportsViewModel : AllSportsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AllSportsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.all_sports_view_layout, container, false)

        val mapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO get The map and manipulate it
        //TODO use ListAdapter to show list in other layout.
        //TODO add also item touchhelper listener

        val listView : ListView = sports_list as ListView
        val go_create_pitch_btn = btn_add_pitch_view as Button

        go_create_pitch_btn.setOnClickListener{
            allSportsViewModel.onAddPitchClicked()
        }

        allSportsViewModel.getAllSports().observe(requireActivity(), Observer{sports ->
            context?.let {
                val adapter = PitchesListAdapter(sports, requireContext())
                listView.adapter =  adapter
                sports.forEach{
                    val tmp = LatLng(it.latitude!!, it.longitude!!)
                    mMap.addMarker(MarkerOptions().position(tmp).title(it.name))
                    val zoomLevel = 16.0f
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, zoomLevel))
                }
            }
        })
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
    }


}