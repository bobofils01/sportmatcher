package com.example.sportmatcher.ui.sports

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.ui.NavigationActivity
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
        setContentView(com.example.sportmatcher.R.layout.map_layout)

        //Toolbar
        setSupportActionBar(findViewById(com.example.sportmatcher.R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        sportName = intent.getStringExtra("SPORT_NAME")

        //Place le nom de du sport dans le toolbar
        supportActionBar?.title = sportName.toLowerCase().capitalize()

        initLiveDatas()
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
                com.example.sportmatcher.R.id.sportHomePageFragment,
                fragment,
                tag
            ).commit()
    }

    //Quand l'utilisateur souhaite faire un retour en arrière
    override fun onBackPressed() {
        startActivity(Intent(this, NavigationActivity::class.java))
    }

    //Si on appuie sur le boutton back du toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, NavigationActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}