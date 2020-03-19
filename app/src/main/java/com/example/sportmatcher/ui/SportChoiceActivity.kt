package com.example.sportmatcher.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import android.widget.Toolbar
import com.example.sportmatcher.R
import com.example.sportmatcher.ui.preferences.PreferencesActivity
import com.example.sportmatcher.ui.sports.SportHomePageActivity
import kotlinx.android.synthetic.main.toolbar.*
import java.nio.file.Files.delete

class SportChoiceActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sport_choice_layout)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sport ->{
            Toast.makeText(this,"T'es déjà dans les sports fdp",Toast.LENGTH_LONG).show()
            true
        }

        R.id.friends -> {
            startActivity(Intent(this, PreferencesActivity::class.java))
            true
        }

        R.id.log_out ->{
            Toast.makeText(this,"Logged out",Toast.LENGTH_LONG).show()
            finish()
            true
        }

        /*android.R.id.home ->{
            Toast.makeText(this,"Home action",Toast.LENGTH_LONG).show()
            true
        }*/

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun onSportClick(v: View) {
        val b = v as Button
        val sportName = b.text.toString()
        val intent = Intent(this, SportHomePageActivity::class.java).apply {
            putExtra("SPORT_NAME", sportName)
        }
        startActivity(intent)
    }

    /*fun showPopup(v: View){
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.toolbar_menu, popup.menu)
        popup.show()
        PopupMenu(this, v).apply {
            setOnMenuItemClickListener(this@SportChoiceActivity)
            inflate(R.menu.toolbar_menu)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.log_out ->{
                finish()
                Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
                true
            }
            else -> false
        }
    }*/

    override fun onBackPressed() { //Empêche l'utilisateur de se déconnecter
    }
}
