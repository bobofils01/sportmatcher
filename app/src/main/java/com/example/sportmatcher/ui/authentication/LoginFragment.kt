package com.example.sportmatcher.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.LoginViewBinding
import com.example.sportmatcher.ui.ForgotPasswordActivity
import com.example.sportmatcher.ui.SignupActivity
import com.example.sportmatcher.ui.map.MapActivity
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import kotlinx.android.synthetic.main.login_layout.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    lateinit var binding: LoginViewBinding

    //view models always initialised like this
    private val viewmodel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_layout, container, false)
        binding.loginViewModel = viewmodel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // listen to buttons
        btn_login.setOnClickListener {
            viewmodel.onLoginClicked()
        }

        signup_btn.setOnClickListener {
            startActivity(Intent(view.context, SignupActivity::class.java))
        }

        forgot_password_btn.setOnClickListener {
            startActivity(Intent(view.context, MapActivity::class.java))
        }

    }


}