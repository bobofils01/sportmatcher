package com.example.sportmatcher.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sportmatcher.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.login_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val signup: Button = findViewById(R.id.signup)
        signup.setOnClickListener(){
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

    }
}
