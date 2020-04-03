package com.example.sportmatcher.ui.preferences

import android.os.Bundle
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.sportmatcher.R
import com.example.sportmatcher.di.ServiceProvider.getAllSportsUseCase
import com.example.sportmatcher.di.ServiceProvider.updateSportsFavouriteUseCase


class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        getAllSportsUseCase.execute().subscribe{sports ->
            val multi_selec_sports = findPreference<MultiSelectListPreference>("favourite_sports_pref_key")
            multi_selec_sports?.entries = sports.toArray(arrayOfNulls(sports.size))
            multi_selec_sports?.entryValues = sports.toArray(arrayOfNulls(sports.size))

            listenerSportsFavouriteChange(multi_selec_sports)
        }

    }

    private fun listenerSportsFavouriteChange(multi_selec_sports: MultiSelectListPreference?) {
        multi_selec_sports?.setOnPreferenceChangeListener { preference, newValue ->
            val updatedList = newValue as (Set<String>)
            updateSportsFavouriteUseCase.execute(ArrayList(updatedList))
            true
        }
    }


}