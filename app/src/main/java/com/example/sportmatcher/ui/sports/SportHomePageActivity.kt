package com.example.sportmatcher.ui.sports

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.sportmatcher.R
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
        setContentView(R.layout.map_layout)

        sportName = intent.getStringExtra("SPORT_NAME")

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
                R.id.sportHomePageFragment,
                fragment,
                tag
            ).commit()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, NavigationActivity::class.java))
    }
}