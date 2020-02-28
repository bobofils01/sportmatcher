package com.example.sportmatcher.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sportmatcher.R
import kotlinx.android.synthetic.main.login_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val login: Button = findViewById(R.id.btn_login)
        login.setOnClickListener {
            //Log.e("TAG", "Email or password incorrect")
            /**
             * Pattern builder, inutile de stocker l'instance builder etc
             */
            AlertDialog.Builder(this).setMessage("Incorrect password or email").create().show()
        }
        /**
         * les findViewById, c'était bien au début d'Android, ajd c'est dépassé, grâce à Kotlin et son view binding,
         * on a accès directement aux views grâce à leur ID
         * https://kotlinlang.org/docs/tutorials/android-plugin.html
         */
        signup.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }

        forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }

    }
}
