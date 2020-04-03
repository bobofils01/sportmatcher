package com.example.sportmatcher.ui.authentication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.databinding.SignupViewBinding
import com.example.sportmatcher.viewModels.authentication.SignupViewModel
import kotlinx.android.synthetic.main.signup_layout.*
import android.text.Editable
import android.text.TextWatcher
import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.domain.utils.isPasswordValid
import com.example.sportmatcher.ui.LoginActivity
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.progress_bar_layout.view.*


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
        binding = DataBindingUtil.inflate(inflater, com.example.sportmatcher.R.layout.signup_layout, container, false)
        binding.signupViewModel = viewmodel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Débloque le boutton Register lorsque les champs sont remplis
        email.addTextChangedListener(loginTextWatcher)
        password.addTextChangedListener(loginTextWatcher)
        confirm_password.addTextChangedListener(loginTextWatcher)

        confirm_register.setOnClickListener {

            //Retire le clavier
            hideKeyboard()

            if(!email.text.toString().isEmailValid())
                email.error = "Please enter a valid mail address."

            else if(!password.text.toString().isPasswordValid()) //Si le mot de passe ne suit pas les conditions
                password.error = "Your password must contain at least 6 characters composed with " +
                        "at least one letter and one number"

            else if(!password.text?.toString().equals(confirm_password.text.toString())!!) {//Si le password et le confirm password ne match pas
                //Erreur
                //confirm_password.setText("")
                //password.setText("")
                confirm_password.error = "Please verify if you correctly confirmed your password."
            }
            else{
                //ProgressBar
                progressBar.pbText.text = "Signing up"
                progressBar.visibility = View.VISIBLE

                viewmodel.onRegisterClicked()
            }
        }
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

        login.setOnClickListener{ //Si l'utilisateur a déjà un compte
            startActivity(activity?.let { it1 -> LoginActivity.getIntent(it1, LoginViewState.SIGNIN) })
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val confirmPassword = confirm_password.text.toString().trim()

            confirm_register.isEnabled = email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun hideKeyboard(){  //Retire le clavier
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}