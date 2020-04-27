package com.example.sportmatcher.ui.preferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.ui.SportChoiceActivity
import com.example.sportmatcher.ui.WelcomeActivity
import com.example.sportmatcher.viewModels.authentication.LogOutViewModel

class PreferencesActivity : AppCompatActivity() {
    private val viewModel: LogOutViewModel by lazy {
        ViewModelProvider(this).get(LogOutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        /*setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)*/
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.preferenceContainer, PreferencesFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
