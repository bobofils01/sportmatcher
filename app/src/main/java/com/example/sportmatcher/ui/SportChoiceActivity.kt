package com.example.sportmatcher.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportmatcher.R
import kotlinx.android.synthetic.main.sport_choice_layout.*


class SportChoiceActivity : AppCompatActivity(){

    /*private val viewModel: LogOutViewModel by lazy {
        ViewModelProvider(this).get(LogOutViewModel::class.java)
    }*/

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sport_choice_layout)
        /*setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        getAllSportsUseCase.execute().subscribe{ sports ->
            sports_choice_list.removeAllViews()

            for(sport in sports){

                val btn_sport = Button(this)
                btn_sport.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(82))
                btn_sport.text = sport.toUpperCase()
                //btn_sport.background = ResourcesCompat.getDrawable(resources, R.drawable.yours, null)
                btn_sport.setOnClickListener{
                    it -> onSportClick(it)
                }

                sports_choice_list.addView(btn_sport)
            }
        }*/

    }

    /*private fun dpToPx(dp: Int): Int {
        val density: Float = resources
            .getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }*/

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sport ->{
            Toast.makeText(this,"Sports",Toast.LENGTH_LONG).show()
            true
        }

        R.id.friends -> {
            startActivity(Intent(this, FriendsActivity::class.java))
            true
        }

        R.id.preferences -> {
            startActivity(Intent(this, PreferencesActivity::class.java))
            true
        }

        R.id.log_out ->{
            viewModel.onLogoutClicked()
            Toast.makeText(this,"Logged out",Toast.LENGTH_LONG).show()
            //finish()
            startActivity(Intent(this, WelcomeActivity::class.java))
            true
        }

        /*android.R.id.home ->{
            Toast.makeText(this,"Home action",Toast.LENGTH_LONG).show()
            true
        }*/

        else -> {
            super.onOptionsItemSelected(item)
        }
    }*/

    /*fun onSportClick(v: View) {
        val b = v as Button
        val sportName = b.text.toString()
        val intent = Intent(this, SportHomePageActivity::class.java).apply {
            putExtra("SPORT_NAME", sportName)
        }
        startActivity(intent)
    }*/
}
