package com.example.sportmatcher.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sportmatcher.R
import com.example.sportmatcher.ui.authentication.LoginViewState


class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_layout)

        val login : Button = findViewById(R.id.btn_login)
        val signup : Button = findViewById(R.id.signup_btn)


        login.setOnClickListener {
            startActivity(LoginActivity.getIntent(this, LoginViewState.SIGNIN))
        }

        signup.setOnClickListener {
            startActivity(LoginActivity.getIntent(this, LoginViewState.SIGNUP))
        }
    }
}
