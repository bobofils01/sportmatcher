package com.example.sportmatcher.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.ui.authentication.LoginFragment
import com.example.sportmatcher.ui.authentication.LoginViewState
import kotlin.math.sign
import com.example.sportmatcher.ui.authentication.SignUpFragment
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import com.google.android.gms.common.api.internal.LifecycleCallback.getFragment
import androidx.lifecycle.Observer

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_layout)

        val login : Button = findViewById(R.id.btn_login)
        val signup : Button = findViewById(R.id.signup_btn)


        login.setOnClickListener({
            startActivity(Intent(this, LoginActivity::class.java))
        })

        signup.setOnClickListener({
            startActivity(Intent(this, LoginActivity::class.java))
        })
    }
}
