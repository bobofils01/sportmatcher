package com.example.sportmatcher.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sportmatcher.R
import com.example.sportmatcher.service.FirebaseAuthService

class MainActivity : AppCompatActivity() {
    var authenticated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO if already logged on start another activity.

        setContentView(R.layout.activity_main)

        //TODO find a way to listen in all activities, not in every Activity
        //set Listener of authenticated user and show the right
        FirebaseAuthService.currentAuthenticatedUser.observe(this, Observer { user ->
            if (authenticated && user == null) {
                authenticated = false
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else if (!authenticated && user != null) {
                authenticated = true
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

        })
    }
}
