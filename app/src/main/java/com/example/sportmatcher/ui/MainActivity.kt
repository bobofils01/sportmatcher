package com.example.sportmatcher.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.sportmatcher.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.login_layout.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.fragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)

        val login: Button = findViewById(R.id.btn_login)
        login.setOnClickListener(){
            //Log.e("TAG", "Email or password incorrect")
            val err: AlertDialog.Builder = AlertDialog.Builder(this)
            err.setMessage("Incorrect password or email")
            err.create().show()
        }

        val signup: Button = findViewById(R.id.signup) //Quand l'utilisateur souhaite créer un compte
        signup.setOnClickListener(){
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        val forgotPassword: Button = findViewById(R.id.forgot_password) //Si l'utilisateur oublie son mot de passe
        forgotPassword.setOnClickListener(){
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
