package com.example.sportmatcher.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.SignupViewBinding
import com.example.sportmatcher.service.FirebaseAuthService
import com.example.sportmatcher.viewModels.authentication.SignupViewModel

class SignupActivity : AppCompatActivity() {

    var authenticated: Boolean = false
    lateinit var binding: SignupViewBinding

    //view models always initialised like this
    private val viewmodel: SignupViewModel by lazy {

        ViewModelProvider(this).get(SignupViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.signup_layout)
        binding.signupViewModel = viewmodel

        val registeBtn: Button = findViewById(R.id.confirm_register)
        registeBtn.setOnClickListener{ viewmodel.onSignupClicked()}

        //set Listener of authenticated user and show the right
        FirebaseAuthService.currentAuthenticatedUser.observe(this, Observer { user ->
            if (authenticated && user == null) {
                authenticated = false
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            } else if (!authenticated && user != null) {
                authenticated = true
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

        })
    }
}
