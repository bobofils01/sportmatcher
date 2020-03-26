package com.example.sportmatcher.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.databinding.LoginViewBinding
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import kotlinx.android.synthetic.main.login_layout.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.R
import android.app.ProgressDialog
import android.view.inputmethod.InputMethodManager
import android.app.Activity




class LoginFragment : Fragment() {

    companion object {
        private const val EXTRA_VILLE = "extraVille"
        fun newInstance(ville:String):Fragment{
            return LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_VILLE, ville)
                }
            }
        }
    }

    lateinit var binding: LoginViewBinding

    //view models always initialised like this
    private val viewmodel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, com.example.sportmatcher.R.layout.login_layout, container, false)
        binding.loginViewModel = viewmodel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ville = arguments?.getString(EXTRA_VILLE)

        // listen to buttons
        btn_login.setOnClickListener {
            val progress = ProgressDialog(activity)
            //mProgress.setTitle("Logging In")
            progress.setMessage("Logging in")
            progress.setCancelable(false)
            progress.isIndeterminate = true
            
            //Retire le clavier
            val imm = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm!!.hideSoftInputFromWindow(getView()!!.windowToken, 0)

            progress.show()

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