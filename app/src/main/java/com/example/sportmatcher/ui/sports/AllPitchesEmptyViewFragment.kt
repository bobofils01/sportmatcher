package com.example.sportmatcher.ui.sports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.viewModels.sports.AllSportsViewModel
import kotlinx.android.synthetic.main.all_sports_empty_view_layout.*

class AllPitchesEmptyViewFragment: Fragment(){

    private val allSportsViewModel : AllSportsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AllSportsViewModel::class.java)
    }

    companion object {
        private const val EXTRA_SPORT = "SPORT_NAME"
        fun newInstance(extra: String): Fragment {
            return AllPitchesEmptyViewFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SPORT, extra)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.all_sports_empty_view_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPitchBtn_1.setOnClickListener{
            allSportsViewModel.onAddPitchClicked()
        }
    }

}