package com.example.sportmatcher.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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
import com.example.sportmatcher.viewModels.authentication.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.welcome_layout.*


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
        /*viewModel.loginViewStateLiveData.value = intent.extras?.get(LoginActivity.SCREEN_STATE_KEY) as LoginViewState
        initLiveDatas()*/
        setContentView(R.layout.welcome_layout)

        val login : Button = findViewById(R.id.btn_login)
        val signup : Button = findViewById(R.id.signup_btn)

        val args = intent.extras
        val isLoggedOut = args?.getBoolean("LOGGING_OUT", false)
        if(args != null && isLoggedOut!!){
            progress.visibility = View.GONE
            login.visibility = View.VISIBLE
            signup.visibility = View.VISIBLE
        }

        val handler = Handler()
        handler.postDelayed({run{

            //Permet d'être directement connecté à l'application
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                val intent = Intent(this, NavigationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            /*else
                Log.d(FragmentActivity.TAG, "onAuthStateChanged:signed_out")*/
            else{
                val progress: ProgressBar = findViewById(R.id.progress)
                progress.visibility = View.GONE

                login.visibility = View.VISIBLE
                signup.visibility = View.VISIBLE
            }

        }}, 2000)

        //Quand l'utilisateur appuie sur le boutton login
        login.setOnClickListener {
            startActivity(LoginActivity.getIntent(this, LoginViewState.SIGNIN))
        }

        //Quand l'utilisateur appuie sur le boutton signup
        signup.setOnClickListener {
            startActivity(LoginActivity.getIntent(this, LoginViewState.SIGNUP))
        }
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
