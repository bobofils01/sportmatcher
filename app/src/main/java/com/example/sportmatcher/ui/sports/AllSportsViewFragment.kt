package com.example.sportmatcher.ui.sports

import android.location.Address
import android.location.Geocoder
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
import java.io.IOException
import java.util.*


class AllSportsViewFragment: Fragment(), OnMapReadyCallback{

    companion object {
        private const val EXTRA_SPORT = "SPORT_NAME"
        fun newInstance(extra: String): Fragment {
            return AllSportsViewFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SPORT, extra)
                }
            }
        }
    }
    private lateinit var mMap: GoogleMap
    private lateinit var sportName: String
    private val allSportsViewModel : AllSportsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AllSportsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sportName = arguments?.getString(EXTRA_SPORT)!!

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
        val goCreatePitchBtn = btn_add_pitch_view as Button

        goCreatePitchBtn.setOnClickListener{
            allSportsViewModel.onAddPitchClicked()
        }

        allSportsViewModel.getAllSports(sportName).observe(requireActivity(), Observer{sports ->
            context?.let {
                val adapter = PitchesListAdapter(sports, requireContext())
                listView.adapter =  adapter
                sports.forEach{
                    val tmp = LatLng(it.latitude!!, it.longitude!!)
                    mMap.addMarker(MarkerOptions().position(tmp).title(it.name))
                }
            }
        })
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        val timeZone = TimeZone.getDefault().id.toString()
        val zone = getLocationFromAddress(timeZone) ?: LatLng(0.0, 0.0)
        val zoomLevel = 10.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zone, zoomLevel))
    }

    private fun getLocationFromAddress(strAddress: String): LatLng? {

        val coder = Geocoder(context)
        val address : List<Address>
        var p1: LatLng? = null

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null
            }
            var location : Address =address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = LatLng((location.getLatitude()), (location.getLongitude()));
        }catch (e: IOException){
            e.stackTrace
        }

        return p1;
    }

}