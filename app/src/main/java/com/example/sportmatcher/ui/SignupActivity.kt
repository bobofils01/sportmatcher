package com.example.sportmatcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.LoginViewBinding
import com.example.sportmatcher.databinding.SignupViewBinding
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import com.example.sportmatcher.viewModels.authentication.SignupViewModel

class SignupActivity : AppCompatActivity() {

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
    }
}
