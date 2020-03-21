package com.example.sportmatcher.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.di.ServiceProvider.getAllSportsUseCase
import com.example.sportmatcher.ui.preferences.PreferencesActivity
import com.example.sportmatcher.ui.sports.SportHomePageActivity
import com.example.sportmatcher.viewModels.authentication.LogOutViewModel
import kotlinx.android.synthetic.main.sport_choice_layout.*


class SportChoiceActivity : AppCompatActivity(){

    private val viewModel: LogOutViewModel by lazy {
        ViewModelProvider(this).get(LogOutViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sport_choice_layout)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        getAllSportsUseCase.execute().subscribe{ sports ->
            sports_choice_list.removeAllViews()

            for(sport in sports){

                val btn_sport = Button(this)
                btn_sport.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(82))
                btn_sport.text = sport.toUpperCase()
                btn_sport.setOnClickListener{
                    it -> onSportClick(it)
                }

                sports_choice_list.addView(btn_sport)
            }
        }

    }

    fun dpToPx(dp: Int): Int {
        val density: Float = resources
            .getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sport ->{
            Toast.makeText(this,"T'es déjà dans les sports",Toast.LENGTH_LONG).show()
            true
        }

        R.id.friends -> {
            startActivity(Intent(this, PreferencesActivity::class.java))
            true
        }

        R.id.log_out ->{
            viewModel.onLogoutClicked()
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

    override fun onBackPressed() { //Empêche l'utilisateur de se déconnecter
    }
}
