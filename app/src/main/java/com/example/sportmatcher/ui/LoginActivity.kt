package com.example.sportmatcher.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.ui.authentication.LoginFragment
import com.example.sportmatcher.ui.authentication.LoginViewState
import com.example.sportmatcher.ui.authentication.SignUpFragment
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.sportmatcher.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.signup_layout.view.*


@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    companion object {
        const val LOGIN_FRAG_TAG = "LoginFragmentTag"
        const val SIGN_UP_FRAG_TAG = "signupFragmentTag"

        const val SCREEN_STATE_KEY = "state"

        fun getIntent(context: Context, state : LoginViewState): Intent {
            return Intent(context, LoginActivity::class.java).apply {
                putExtra(SCREEN_STATE_KEY, state)
            }
        }
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel.loginViewStateLiveData.value = intent.extras?.get(SCREEN_STATE_KEY) as LoginViewState
        initLiveDatas()
    }

    private fun initLiveDatas(){

        val progress = ProgressDialog(this)
        //mProgress.setTitle("Logging In")
        progress.setMessage("Logging in")
        progress.setCancelable(false)
        progress.isIndeterminate = true

        viewModel.getAuthenticationStateLiveData().observe(this, Observer {
            it?.let { state ->
                when (state) {
                    is AuthenticatedState -> {
                        progress.show()

                        val intent = Intent(this, SportChoiceActivity::class.java)
                        startActivity(intent)
                    }
                    is AuthenticationInProgress -> {
                        progress.show()
                    }
                    else -> {
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        viewModel.getLoginViewStateLiveData().observe(this, Observer {
            it?.let{
                    state ->
                when(state) {
                    LoginViewState.SIGNIN -> {
                        setFragment(getFragment(LOGIN_FRAG_TAG), LOGIN_FRAG_TAG)
                    }
                    LoginViewState.SIGNUP ->{
                        setFragment(getFragment(SIGN_UP_FRAG_TAG), SIGN_UP_FRAG_TAG)
                    }
                }
            }
        })
    }

    private fun getFragment(tag: String):Fragment{
        return when (tag) {
            LOGIN_FRAG_TAG -> LoginFragment.newInstance("vide")
            SIGN_UP_FRAG_TAG -> SignUpFragment.newInstance("dhdh")
            else -> throw IllegalArgumentException("Key doesn't exist")
        }
    }

    private fun setFragment(fragment: Fragment, tag: String? = null) {
        supportFragmentManager.beginTransaction()
            .replace(
                com.example.sportmatcher.R.id.loginContainer,
                fragment,
                tag
            ).commit()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
