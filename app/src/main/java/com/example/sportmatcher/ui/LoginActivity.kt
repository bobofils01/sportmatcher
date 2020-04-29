package com.example.sportmatcher.ui

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
import kotlinx.android.synthetic.main.signup_layout.*


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
        setContentView(com.example.sportmatcher.R.layout.activity_login)
        viewModel.loginViewStateLiveData.value = intent.extras?.get(SCREEN_STATE_KEY) as LoginViewState
        initLiveDatas()
    }

    private fun initLiveDatas(){
        viewModel.getAuthenticationStateLiveData().observe(this, Observer {
            it?.let { state ->
                when (state) {
                    is AuthenticatedState -> {
                        val intent = Intent(this, NavigationActivity::class.java)
                        startActivity(intent)
                    }
                    is AuthenticationInProgress -> {}
                    else -> {
                        //Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
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

    //Quand l'utilisateur souhaite faire un retour en arrière si qui cause l'arrêt de la création de son compte
    override fun onBackPressed(){
        viewModel.getLoginViewStateLiveData().observe(
            this, Observer {
                it?.let{ state ->
                    when(state) {
                        LoginViewState.SIGNIN -> {}
                        LoginViewState.SIGNOUT -> {}
                        LoginViewState.SIGNUP ->{
                            when {
                                enter_email.visibility == View.GONE -> enter_email.visibility = View.VISIBLE
                                name.visibility == View.GONE -> name.visibility = View.VISIBLE


                                //Affiche le message de prévention comme quoi l'utilisateur va quitter la création du compte
                                else -> AlertDialog.Builder(this)
                                    .setTitle("You are about to stop creating your account")
                                    .setMessage("You will lose all progress you've made")
                                    .setPositiveButton("Continue", null)
                                    .setNegativeButton("Stop"){ _, _ -> super.onBackPressed() }
                                    //.setIcon(android.R.drawable.ic_dialog_alert)
                                    .show()
                                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1CA6BE"))

                                //.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1CA6BE"))
                            }
                        }
                    }
                }
            })

    }

    //Si on appuie sur le boutton back du toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setSupportActionBar(findViewById(com.example.sportmatcher.R.id.toolbar))

        return when (item.itemId) {
            R.id.home -> {

                when {
                    welcome.visibility == View.VISIBLE -> super.onBackPressed()

                    enter_email.visibility == View.GONE ->{
                        enter_email.visibility = View.VISIBLE

                        //Place titre dans le toolbar
                        supportActionBar?.title = "Email"
                    }
                    name.visibility == View.GONE ->{
                        name.visibility = View.VISIBLE

                        //Place titre dans le toolbar
                        supportActionBar?.title = "Name"
                    }

                    //Affiche le message de prévention comme quoi l'utilisateur va quitter la création du compte
                    else -> AlertDialog.Builder(this)
                        .setTitle("You are about to stop creating your account")
                        .setMessage("You will lose all progress you've made")
                        .setPositiveButton("Continue", null)
                        .setNegativeButton("Stop"){ _, _ -> super.onBackPressed() }
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                        .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1CA6BE"))

                    //.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1CA6BE"))
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
