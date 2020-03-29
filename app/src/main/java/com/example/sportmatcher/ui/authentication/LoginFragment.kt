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
import android.view.inputmethod.InputMethodManager
import android.app.Activity
import android.graphics.Color
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.signup_layout.*
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.res.ResourcesCompat
import android.R.attr.name
import android.text.TextUtils
import android.R.attr.name
import android.graphics.drawable.Drawable
import android.R.attr.name
import android.content.Context
import androidx.lifecycle.Observer
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.ui.LoginActivity
import com.example.sportmatcher.ui.SportChoiceActivity
import com.google.android.gms.common.api.internal.LifecycleCallback.getFragment
import kotlinx.android.synthetic.main.progress_bar_layout.view.*
import java.util.regex.Pattern


@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

    //Pattern permettant de vérifier si l'adresse attribuée est valide
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

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

        editTextEmailID.addTextChangedListener(loginTextWatcher)
        editTextPasswordID.addTextChangedListener(loginTextWatcher)

        // listen to buttons
        btn_login.setOnClickListener {

            //Retire le clavier
            hideKeyboard()

            if(!isValidEmail(editTextEmailID.text.toString()))
                editTextEmailID.error = "Please enter a valid mail address."

            else{
                llProgressBar.pbText.text = "Logging in"
                llProgressBar.visibility = View.VISIBLE

                viewmodel.onLoginClicked()
                viewmodel.getAuthenticationStateLiveData().observe(viewLifecycleOwner, Observer {
                    it?.let { state ->
                        when (state) {
                            is AuthenticatedState -> {}
                            is AuthenticationInProgress -> {}
                            else -> {
                                llProgressBar.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }

        signup_btn.setOnClickListener {
            viewmodel.onSignUpClicked()
        }

        forgot_password_btn.setOnClickListener {
            startActivity(Intent(view.context, ForgotPasswordActivity::class.java))
        }

    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val usernameInput = editTextEmailID.text.toString().trim()
            val passwordInput = editTextPasswordID.text.toString().trim()

            btn_login.isEnabled = usernameInput.isNotEmpty() && passwordInput.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun hideKeyboard(){  //Retire le clavier
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun isValidEmail(email: String): Boolean { //Vérifie si l'adresse mail est valide
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }
}

