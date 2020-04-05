package com.example.sportmatcher.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.ui.preferences.PreferencesActivity
import com.example.sportmatcher.viewModels.authentication.LogOutViewModel

class FriendsActivity : AppCompatActivity() {

    private val viewModel: LogOutViewModel by lazy{
        ViewModelProvider(this).get(LogOutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friends_layout)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sport ->{
            startActivity(Intent(this, SportChoiceActivity::class.java))
            true
        }

        R.id.preferences -> {
            startActivity(Intent(this, PreferencesActivity::class.java))
            true
        }

        R.id.friends -> {
            Toast.makeText(this, "T'es déjà dans les amis", Toast.LENGTH_LONG).show()
            true
        }

        R.id.log_out -> {
            viewModel.onLogoutClicked()
            Toast.makeText(this,"Logged out",Toast.LENGTH_LONG).show()
            startActivity(Intent(this, WelcomeActivity::class.java))
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
