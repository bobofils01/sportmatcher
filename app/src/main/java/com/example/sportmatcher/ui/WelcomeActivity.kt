package com.example.sportmatcher.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
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
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import com.google.android.gms.common.api.internal.LifecycleCallback.getFragment


class WelcomeActivity : AppCompatActivity(){

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
        setContentView(R.layout.welcome_layout)

        /*viewModel.loginViewStateLiveData.value = intent.extras?.get(LoginActivity.SCREEN_STATE_KEY) as LoginViewState
        initLiveDatas()*/

        val login : Button = findViewById(R.id.btn_login)
        val signup : Button = findViewById(R.id.signup_btn)

        login.setOnClickListener {
            startActivity(LoginActivity.getIntent(this, LoginViewState.SIGNIN))
        }

        signup.setOnClickListener {
            startActivity(LoginActivity.getIntent(this, LoginViewState.SIGNUP))
        }
    }

    private fun initLiveDatas(){
        viewModel.getAuthenticationStateLiveData().observe(this, Observer {
            it?.let { state ->
                when (state) {
                    is AuthenticatedState -> {
                        /*val progressBar: ProgressBar = findViewById(R.id.progress_bar)
                        progressBar.visibility = View.VISIBLE*/
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
                        setFragment(getFragment(LoginActivity.LOGIN_FRAG_TAG),
                            LoginActivity.LOGIN_FRAG_TAG
                        )
                    }
                }
            }
        })
    }

    private fun getFragment(tag: String): Fragment {
        return when (tag) {
            LoginActivity.LOGIN_FRAG_TAG -> LoginFragment.newInstance("vide")
            //LoginActivity.SIGN_UP_FRAG_TAG -> SignUpFragment.newInstance("dhdh")
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

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
