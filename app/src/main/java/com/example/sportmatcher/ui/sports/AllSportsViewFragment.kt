package com.example.sportmatcher.ui.sports

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.PitchesListAdapter
import com.example.sportmatcher.viewModels.sports.AllSportsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.all_sports_view_layout.*
import java.io.IOException
import java.util.*




@Suppress("DEPRECATION")
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

        private const val PERMISSION_ID = 42
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val goCreatePitchBtn = btn_add_pitch_view as Button
        /*goCreatePitchBtn.setBackgroundResource(R.drawable.button_enabled)
        goCreatePitchBtn.setTextColor(resources.getColor(R.color.colorWhite))
        //goCreatePitchBtn.gravity = Gravity.CENTER*/

        goCreatePitchBtn.setOnClickListener{
            allSportsViewModel.onAddPitchClicked()
        }

        /*fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                val pos = LatLng((location!!.latitude), (location.longitude))
                Log.d("Roman AlSF", pos.toString())
                mMap.addMarker(MarkerOptions().position(pos).title("Me"))
            }
        */

        getLastLocation()

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

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    var location: Location? = task.result
                    if (location != null) {
                        val pos = LatLng((location!!.latitude), (location.longitude))
                        Log.d("Roman AlSF", pos.toString())
                        mMap.addMarker(MarkerOptions().position(pos).title("Your Position").alpha(0.5f))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 10.0f))
                    }
                }
            } else {
                Log.d("Roman AlSF", "Turn on location")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            Companion.PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == Companion.PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
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
            var location : Address = address[0];
            location.latitude;
            location.longitude;

            p1 = LatLng((location.latitude), (location.longitude));
        }catch (e: IOException){
            e.stackTrace
        }

        return p1;
    }

}