package com.example.sportmatcher.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.databinding.LoginViewBinding
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import kotlinx.android.synthetic.main.login_layout.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.R
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.signup_layout.*
import android.text.Editable
import android.text.TextWatcher
import android.app.AlertDialog
import android.graphics.Color
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import kotlinx.android.synthetic.main.email_layout.*
import kotlinx.android.synthetic.main.progress_bar_layout.view.*
import java.lang.Character.isWhitespace


@Suppress("DEPRECATION")
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

        editTextEmailID.addTextChangedListener(loginTextWatcher)
        editTextPasswordID.addTextChangedListener(loginTextWatcher)


        // listen to buttons
        btn_login.setOnClickListener {
            if(editTextEmailID.text.toString().endsWith(" ")) //Vérifie s'il n'y a pas d'espace en trop dans l'adresse mail
                editTextEmailID.setText(editTextEmailID.text.toString().trim())

            //Retire le clavier
            hideKeyboard()

            if(!editTextEmailID.text.toString().isEmailValid())
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
                                val handler = Handler()
                                handler.postDelayed({run{
                                    llProgressBar.visibility = View.GONE

                                    AlertDialog.Builder(context)
                                        .setTitle("Incorrect username or password")
                                        .setMessage("The mail address or the password you entered is incorrect. Please try again.")
                                        /*.setPositiveButton(R.string.yes, DialogInterface.OnClickListener() {
                                            void onClick(DialogInterface dialog, int which){}})*/
                                        .setNegativeButton(R.string.ok, null)
                                        //.setIcon(android.R.drawable.ic_dialog_alert)
                                        .show()
                                        .getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1CA6BE"))
                                } }, 5000)
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
        val imm = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}

