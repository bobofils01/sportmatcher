package com.example.sportmatcher.ui.authentication

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.domain.utils.isPasswordValid
import com.example.sportmatcher.ui.LoginActivity
import com.example.sportmatcher.ui.utils.UIUtils
import com.example.sportmatcher.viewModels.authentication.SignupViewModel
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.email_layout.*
import kotlinx.android.synthetic.main.name_layout.*
import kotlinx.android.synthetic.main.password_layout.*
import kotlinx.android.synthetic.main.progress_bar_layout.view.*
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

    //view models always initialised like this
    private val viewModel: SignupViewModel by lazy {
        ViewModelProvider(this).get(SignupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         return inflater.inflate(com.example.sportmatcher.R.layout.signup_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        first_name.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        last_name.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        first_name.addTextChangedListener(signUpTextWatcher)
        last_name.addTextChangedListener(signUpTextWatcher)

        //first name
        UIUtils.addOnTextViewChange(first_name, viewModel.firstName)
        //last name
        UIUtils.addOnTextViewChange(last_name, viewModel.lastName)
        //email
        UIUtils.addOnTextViewChange(email, viewModel.email)
        //password
        UIUtils.addOnTextViewChange(password, viewModel.password)

        next1.setOnClickListener{
            when {
                first_name.text.toString().matches(".*\\d.*".toRegex()) -> first_name.error = "Your name can't contain numbers"
                last_name.text.toString().matches(".*\\d.*".toRegex()) -> last_name.error = "Your name can't contain numbers"
                else -> {
                    hideKeyboard()

                    name.visibility = View.GONE
                }
            }
        }

        //Débloque le boutton Register lorsque les champs sont remplis
        email.addTextChangedListener(signUpTextWatcher)

        next2.setOnClickListener{

            if(email.text.toString().endsWith(" ")) //Vérifie s'il n'y a pas d'espace en trop dans l'adresse mail
                email.setText(email.text.toString().trim())

            when{!email.text.toString().isEmailValid() -> email.error = "Please enter a valid mail address."

                else -> {
                    hideKeyboard()


                    enter_email.visibility = View.GONE
                }
            }
        }

        password.addTextChangedListener(signUpTextWatcher)

        confirm_register.setOnClickListener {

            //Retire le clavier
            hideKeyboard()

            if(!password.text.toString().isPasswordValid()) //Si le mot de passe ne suit pas les conditions
                password.error = "Your password must contain at least 6 characters composed with " +
                        "at least one letter and one number"

            else{
                //ProgressBar
                progressBar.pbText.text = "Signing up"
                progressBar.visibility = View.VISIBLE


                try{
                    viewModel.onRegisterClicked()
                }
                catch (e: FirebaseAuthUserCollisionException){ //Dans le cas où l'email a déjà été utilisé
                    startActivity(activity?.let { it1 -> LoginActivity.getIntent(it1, LoginViewState.SIGNIN) })
                    Toast.makeText(activity, "You've already signed up with this email you may sign in instead", Toast.LENGTH_LONG).show()
                }
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

    private val signUpTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val firstName = first_name.text.toString().trim()
            val lastName = last_name.text.toString().trim()

            next1.isEnabled = firstName.isNotEmpty() && lastName.isNotEmpty()

            val  email = first_name.text.toString().trim()

            next2.isEnabled = email.isNotEmpty()

            val password = password.text.toString().trim()

            confirm_register.isEnabled = password.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun hideKeyboard(){  //Retire le clavier
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    /*fun isCheckEmail(email: String, listener: OnEmailCheckListener) {
        mAuth.fetchProvidersForEmail(email)
            .addOnCompleteListener(OnCompleteListener<ProviderQueryResult> { task ->
                val check = !task.result!!.providers!!.isEmpty()

                listener.onSucess(check)
            })

    }*/
}
