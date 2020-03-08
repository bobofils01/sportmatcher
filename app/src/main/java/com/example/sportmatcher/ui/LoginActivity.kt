package com.example.sportmatcher.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import com.example.sportmatcher.R
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.ui.authentication.LoginFragment
import com.example.sportmatcher.ui.authentication.LoginViewState
import com.example.sportmatcher.ui.authentication.SignUpFragment
import com.example.sportmatcher.ui.sports.AddPitchViewFragment
import com.example.sportmatcher.viewModels.authentication.LoginViewModel

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val LOGIN_FRAG_TAG = "LoginFragmentTag"
        private const val SIGNUP_FRAG_TAG = "signupFragmentTag"

        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {
                putExtra("key", "value")
            }
        }
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        intent.extras?.getString("key")
        initLiveDatas()
    }

    private fun initLiveDatas(){
        viewModel.getAuthenticationStateLiveDate().observe(this, Observer {
            it?.let { state ->
                when (state) {
                    is AuthenticatedState -> {
                        val intent = Intent(this, SportChoiceActivity::class.java)
                        startActivity(intent)
                    }
                    is AuthenticationInProgress -> {
                        //TODO show progress bar
                        Toast.makeText(this, "In progress", Toast.LENGTH_LONG).show()
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
                        setFragment(getFragment(SIGNUP_FRAG_TAG), SIGNUP_FRAG_TAG)
                    }
                }
            }
        })
    }

    private fun getFragment(tag: String):Fragment{
        return when (tag) {
            LOGIN_FRAG_TAG -> LoginFragment.newInstance("vide")
            SIGNUP_FRAG_TAG -> AddPitchViewFragment.newInstance("dhdh")
            else -> throw IllegalArgumentException("Key doesn't exist")
        }
    }

    private fun setFragment(fragment: Fragment, tag: String? = null) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.loginContainer,
                fragment,
                tag
            ).commit()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
