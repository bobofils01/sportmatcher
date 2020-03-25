package com.example.sportmatcher.ui.sports

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.sportmatcher.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.ui.LoginActivity
import com.example.sportmatcher.ui.SportChoiceActivity
import com.example.sportmatcher.viewModels.sports.AllSportsViewModel

class SportHomePageActivity : AppCompatActivity() {

    companion object {
        private const val ADD_PITCH_FRAG_TAG = "AddPitchFragmentTag"
        private const val ALL_PITCHES_FRAG_TAG = "AllSportsFragmentTag"
        private const val ALL_PITCHES_EMPTY_FRAG_TAG = "AllSportsEmptyFragmentTag"
    }

    private lateinit var sportName: String

    private val allSportsViewModel : AllSportsViewModel by lazy {
        ViewModelProvider(this).get(AllSportsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        sportName = intent.getStringExtra("SPORT_NAME")

        initLiveDatas()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sport ->{
            //startActivity(Intent(this, SportChoiceActivity::class.java))
            true
        }

        R.id.friends -> {
            //Toast.makeText(this, "T'es déjà dans les amis", Toast.LENGTH_LONG).show()
            true
        }

        R.id.log_out ->{
            //startActivity(Intent(this, LoginActivity::class.java))
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


    private fun initLiveDatas(){
        allSportsViewModel.getViewAddPitchClickedLiveData().observe(this, Observer{
            it?.let{ state ->
                when(state) {
                    true -> {
                        setFragment(getFragment(ADD_PITCH_FRAG_TAG), ADD_PITCH_FRAG_TAG)
                    }
                    false ->{
                        allSportsViewModel.getAllSports(sportName.toLowerCase()).observe(this, Observer {
                                pitches ->
                            if(pitches.isEmpty()) {
                                Log.d("SPORTHOMEACTIVITY", pitches.size.toString())
                                setFragment(getFragment(ALL_PITCHES_EMPTY_FRAG_TAG), ALL_PITCHES_EMPTY_FRAG_TAG)
                            }else{
                                setFragment(getFragment(ALL_PITCHES_FRAG_TAG), ALL_PITCHES_FRAG_TAG)
                            }
                        }
                        )
                    }
                }
            }
        })
    }

    private fun getFragment(tag: String): Fragment {
        return when (tag) {
            ADD_PITCH_FRAG_TAG -> AddPitchViewFragment.newInstance(sportName)
            ALL_PITCHES_FRAG_TAG -> AllSportsViewFragment.newInstance(sportName)
            ALL_PITCHES_EMPTY_FRAG_TAG -> AllPitchesEmptyViewFragment.newInstance(sportName)
            else -> throw IllegalArgumentException("Key doesn't exist")
        }
    }

    private fun setFragment(fragment: Fragment, tag: String? = null) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.sportHomePageFragment,
                fragment,
                tag
            ).commit()
    }
}