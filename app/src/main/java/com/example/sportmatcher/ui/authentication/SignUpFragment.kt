package com.example.sportmatcher.ui.authentication

import android.R
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.domain.utils.isEmailValid
import com.example.sportmatcher.domain.utils.isPasswordValid
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.ui.LoginActivity
import com.example.sportmatcher.ui.utils.UIUtils
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import com.example.sportmatcher.viewModels.authentication.SignupViewModel
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.email_layout.*
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.name_layout.*
import kotlinx.android.synthetic.main.password_layout.*
import kotlinx.android.synthetic.main.progress_bar_layout.view.*
import kotlinx.android.synthetic.main.sign_up_first_step.*
import kotlinx.android.synthetic.main.signup_layout.*
import androidx.appcompat.app.AppCompatActivity
import android.R.attr.name
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.sportmatcher.ui.NavigationActivity


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

    private val viewmodelLogin: LoginViewModel by lazy {
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
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

        //Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar?)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        //Place un titre dans le toolbar
        (activity as AppCompatActivity).supportActionBar?.title = "Create an account"

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

        //Ecran d'acceuil
        first_next.setOnClickListener{
            welcome.visibility = View.GONE

            //Place un titre dans le toolbar
            (activity as AppCompatActivity).supportActionBar?.title = "Name"
        }

        //Lorsque l'utilisateur a entré son nom et prénom
        next1.setOnClickListener{
            when {
                //Vérifie que le nom et prénom ne contiennent pas de chiffres
                first_name.text.toString().matches(".*\\d.*".toRegex()) -> first_name.error = "Your name can't contain numbers"
                last_name.text.toString().matches(".*\\d.*".toRegex()) -> last_name.error = "Your name can't contain numbers"
                else -> {
                    //Cache le clavier
                    hideKeyboard()

                    name.visibility = View.GONE

                    //Place un titre dans le toolbar
                    (activity as AppCompatActivity).supportActionBar?.title = "Email address"
                }
            }
        }

        //Débloque le boutton Register lorsque les champs sont remplis
        email.addTextChangedListener(signUpTextWatcher)

        next2.setOnClickListener{//Lorsque l'utilisateur à entré son adresse mail

            if(email.text.toString().endsWith(" ")) //Vérifie s'il n'y a pas d'espace en trop dans l'adresse mail
                email.setText(email.text.toString().trim())

            //Si l'adresse mail n'est pas valide
            when{!email.text.toString().isEmailValid() -> email.error = "Please enter a valid mail address."

                else -> {
                    //Cache le clavier
                    hideKeyboard()

                    enter_email.visibility = View.GONE
                    //Place un titre dans le toolbar
                    (activity as AppCompatActivity).supportActionBar?.title = "Password"
                }
            }
        }

        password.addTextChangedListener(signUpTextWatcher)

        confirm_register.setOnClickListener { //Dernière étape de l'inscription

            //Retire le clavier
            hideKeyboard()

            //Si le mot de passe ne suit pas les conditions
            if(!password.text.toString().isPasswordValid())
                password.error = "Your password must contain at least 6 characters composed with " +
                        "at least one letter and one number"

            else{
                //Retire le toolbar
                toolbar.visibility = View.GONE

                //ProgressBar
                progressBar.pbText.text = "Signing up"
                progressBar.visibility = View.VISIBLE

                try{ //L'utilisateur est alors inscrit si l'adresse mail qu'il a donné n'a pas encore été utilisée
                    viewModel.onRegisterClicked()
                }
                catch (e: FirebaseAuthUserCollisionException){ //Dans le cas où l'email a déjà été utilisé
                    startActivity(activity?.let { it1 -> LoginActivity.getIntent(it1, LoginViewState.SIGNIN) })
                    Toast.makeText(activity, "You've already signed up with this email you may sign in instead", Toast.LENGTH_LONG).show()
                }

                //Dans le cas où l'email a déjà été utilisé
                viewmodelLogin.getAuthenticationStateLiveData().observe(viewLifecycleOwner, Observer {
                    it?.let { state ->
                        when (state) {
                            is AuthenticatedState -> {}
                            is AuthenticationInProgress -> {}
                            else -> {
                                val handler = Handler()
                                handler.postDelayed({run{
                                    //Replace le toolbar
                                    toolbar.visibility = View.VISIBLE

                                    progressBar.visibility = View.GONE
                                    enter_email.visibility = View.VISIBLE

                                    AlertDialog.Builder(context)
                                        .setTitle("Mail adress error.")
                                        .setMessage("The email address you entered is already used. Please use another.")
                                        .setNegativeButton(R.string.ok, null)
                                        //.setIcon(android.R.drawable.ic_dialog_alert)
                                        .show()
                                        .getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1CA6BE"))
                                } }, 3000)
                            }
                        }
                    }
                })
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

        //Si l'utilisateur a déjà un compte
        login.setOnClickListener{
            startActivity(activity?.let { it1 -> LoginActivity.getIntent(it1, LoginViewState.SIGNIN) })
        }
    }

    //Débloque les bouttons dès que les champs vides sont remplis
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

    //Retire le clavier
    private fun hideKeyboard(){
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}
