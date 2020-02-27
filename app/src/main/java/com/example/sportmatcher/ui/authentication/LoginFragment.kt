package com.example.sportmatcher.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.LoginViewBinding
import com.example.sportmatcher.ui.ForgotPasswordActivity
import com.example.sportmatcher.ui.SignupActivity
import com.example.sportmatcher.viewModels.authentication.LoginViewModel

class LoginFragment : Fragment() {

    lateinit var binding: LoginViewBinding

    //view models always initialised like this
    private val viewmodel: LoginViewModel by lazy {

        ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    companion object {
        fun newInstance() = LoginFragment()
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

        // listern to buttons

        val loginButton: Button = view.findViewById(R.id.btn_login)
        loginButton.setOnClickListener{
            viewmodel.onLoginClicked()
        }

        val signup: Button = view.findViewById(R.id.signup_btn) //Quand l'utilisateur souhaite créer un compte
        signup.setOnClickListener{
            val intent = Intent(view.context, SignupActivity::class.java)
            startActivity(intent)
        }

        val forgotPassword: Button = view.findViewById(R.id.forgot_password_btn) //Si l'utilisateur oublie son mot de passe
        forgotPassword.setOnClickListener{
            val intent = Intent(view.context, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


    }


}