package com.example.sportmatcher.ui.map

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
import com.example.sportmatcher.ui.authentication.LoginFragment
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import kotlinx.android.synthetic.main.login_layout.*

class MapFragment: Fragment() {

    companion object {
        private const val EXTRA_VILLE = "extraVille"
        fun newInstance(ville:String):Fragment{
            return MapFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_VILLE, ville)
                }
            }
        }
    }

    lateinit var binding: LoginViewBinding

    private val viewmodel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
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
        //val ville = arguments?.getString(LoginFragment.EXTRA_VILLE)
        // listen to buttons
        btn_login.setOnClickListener {
            viewmodel.onLoginClicked()
        }

        signup_btn.setOnClickListener {
            viewmodel.onSignUpClicked()
        }
        forgot_password_btn.setOnClickListener {
            startActivity(Intent(view.context, ForgotPasswordActivity::class.java))
        }

    }


}