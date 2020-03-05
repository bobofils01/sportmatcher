package com.example.sportmatcher.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.sportmatcher.R
import com.example.sportmatcher.domain.sport.AddPitchUseCase
import com.example.sportmatcher.domain.sport.GetAllPitchesUseCase
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.repository.FirebasePitchesRepository
import com.example.sportmatcher.repository.IPitchesRepository
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_layout)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val aPUC = AddPitchUseCase(FirebasePitchesRepository())
        aPUC.execute(Pitch(address = "Rue Du Foyer Schaerbeekois 23", sport = "PingPong", latitude = 30.2, longitude = 32.9))
        Log.d("FYN", "AFTER ADD")
        /*val aPUC = GetAllPitchesUseCase(FirebasePitchesRepository())
        val pitches = aPUC.execute()
        pitches.forEach{
            print("pitch "+ it)
        }*/
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
