package com.example.sportmatcher.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.SignupViewBinding
import com.example.sportmatcher.viewModels.authentication.SignupViewModel
import kotlinx.android.synthetic.main.signup_layout.*

class SignUpFragment : Fragment() {

    companion object {
        private const val EXTRA_VILLE = "extraVille"
        fun newInstance(ville:String):Fragment{
            return SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_VILLE, ville)
                }
            }
        }
    }

    lateinit var binding: SignupViewBinding

    //view models always initialised like this
    private val viewmodel: SignupViewModel by lazy {
        ViewModelProvider(this).get(SignupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.signup_layout, container, false)
        binding.signupViewModel = viewmodel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm_register.setOnClickListener { viewmodel.onRegisterClicked() }
/*
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

        })*/
    }
}