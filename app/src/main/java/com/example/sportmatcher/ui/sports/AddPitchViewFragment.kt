package com.example.sportmatcher.ui.sports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.AddPitchViewBinding
import com.example.sportmatcher.viewModels.sports.AddPitchViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.add_pitch_layout.*


class AddPitchViewFragment: Fragment(){

    companion object {
        private const val EXTRA_SPORT = "SPORT_NAME"
        fun newInstance(extra: String): Fragment {
            return AddPitchViewFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SPORT, extra)
                }
            }
        }
    }

    lateinit var binding: AddPitchViewBinding

    private val viewmodel: AddPitchViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AddPitchViewModel::class.java)
    }

    lateinit var autocompleteFragment: AutocompleteSupportFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get the sportID from the extra bundle
        viewmodel.sport.value = arguments?.getString(EXTRA_SPORT)

        binding = DataBindingUtil.inflate(inflater, R.layout.add_pitch_layout, container, false)
        binding.addPitchViewModel = viewmodel

        // Initialize the AutocompleteSupportFragment.
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment


        if (!Places.isInitialized()) {
            Places.initialize(activity!!.applicationContext, getString(R.string.google_maps_key));
        }

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )
        
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setHint("Enter Location").setCountries("BE").setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) { // TODO: Get info about the selected place.
                Log.d("PlaceTAG", "Place: " + place.name + ", " + place.id + " lat :lng " + place.latLng)
                viewmodel.address.value = place.address
                viewmodel.latitude.value = place.latLng?.latitude
                viewmodel.longitude.value = place.latLng?.longitude
                viewmodel.sport.value = arguments?.getString(EXTRA_SPORT)
            }

            override fun onError(status: Status) { // TODO: Handle the error.
                Log.d("PlaceTAG", "An error occurred: $status")
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  val fields: List<Place.Field> =
              Arrays.asList(Place.Field.ID, Place.Field.NAME)

          val intent = Autocomplete.IntentBuilder(
              AutocompleteActivityMode.FULLSCREEN, fields
          ).build(requireContext())

          startActivityForResult(intent, 1)

         */

        addPitchBtn.setOnClickListener {
            viewmodel.onAddPitchClicked()
        }

        /*
        addImageBtn.setOnClickListener {
            //Create an Intent with action as ACTION_PICK
            val intent= Intent(Intent.ACTION_PICK);
            // Sets the type as image/. This ensures only components of type image are selected
            intent.type = "image/";
            //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
            val mimeTypes :Array<String> = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
            // Launching the Intent
            startActivity(intent)
            startActivityForResult(intent, 200)

        }
        */
    }

}