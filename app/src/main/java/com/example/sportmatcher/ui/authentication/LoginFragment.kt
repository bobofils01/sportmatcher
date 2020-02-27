package com.example.sportmatcher.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.LoginViewBinding
import com.example.sportmatcher.viewModels.authentication.LoginViewModel

class LoginFragment :  Fragment(){

    lateinit var binding: LoginViewBinding
    lateinit var viewmodel: LoginViewModel

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = LoginViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_layout, container, false)
        viewmodel = LoginViewModel()
        binding.loginViewModel = viewmodel

        //not sure
        binding.lifecycleOwner = this

        return binding.root
    }


}