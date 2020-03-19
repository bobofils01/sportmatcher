package com.example.sportmatcher.ui.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.sportmatcher.R
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.updateSportsFavouriteUsecase


class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        //TODO to get from a use case
        val sports = ArrayList<String>()
        sports.add("BasketBall")
        sports.add("Football")
        sports.add("Tennis")

       val multi_selec_sports = findPreference<MultiSelectListPreference>("favourite_sports_pref_key")
        multi_selec_sports?.entries = sports.toArray(arrayOfNulls(sports.size))
        multi_selec_sports?.entryValues = sports.toArray(arrayOfNulls(sports.size))

        listenerSportsFavouriteChangement(multi_selec_sports)
    }

    private fun listenerSportsFavouriteChangement(multi_selec_sports: MultiSelectListPreference?) {
        multi_selec_sports?.setOnPreferenceChangeListener { preference, newValue ->
            val updatedList = newValue as (Set<String>)
            updateSportsFavouriteUsecase.execute(ArrayList(updatedList))
            true
        }
    }


}