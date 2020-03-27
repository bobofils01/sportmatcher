package com.example.sportmatcher.ui.authentication

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.databinding.SignupViewBinding
import com.example.sportmatcher.viewModels.authentication.SignupViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.signup_layout.*
import java.util.regex.Pattern
import android.R




class SignUpFragment : Fragment() {

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
        confirm_register.setOnClickListener {

            val progress = ProgressDialog(activity)
            //mProgress.setTitle("Logging In")
            progress.setMessage("Signing up")
            progress.setCancelable(false)
            progress.isIndeterminate = true

            //Retire le clavier
            hideKeyboard()

            //ProgressBar
            progress.show()

            if(!isValidEmail(email.text.toString())){
                //Retire la bar de progression
                progress.hide()

                email.error = "Please enter a valid mail address."
            }
            else if(password.text.toString().length < 6){ //Si le mot de passe n'est pas assez long
                progress.hide()

                password.error = "Your password must contain at least 6 characters"

            }
            else if(!password.text?.toString().equals(confirm_password.text.toString())!!) {//Si le password et le confirm password ne match pas
                progress.hide()

                //Erreur
                confirm_password.setText("")
                password.setText("")
                confirm_password.error = "Please verify if you correctly confirmed your password."
            }
            else
                viewmodel.onRegisterClicked() }
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

    fun isValidEmail(email: String): Boolean { //Vérifie si l'adresse mail est valide
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun hideKeyboard(){  //Retire le clavier
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(getView()!!.windowToken, 0)
    }
}