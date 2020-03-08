package com.example.sportmatcher.ui.sports

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
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
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.add_pitch_layout.*
import java.util.*


class AddPitchViewFragment: Fragment() {

    companion object {
        private const val EXTRA_VILLE = "extraVille"
        fun newInstance(ville: String): Fragment {
            return AddPitchViewFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_VILLE, ville)
                }
            }
        }
    }

    lateinit var binding: AddPitchViewBinding

    private val viewmodel: AddPitchViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AddPitchViewModel::class.java)
    }

    lateinit var autocompleteFragment: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the AutocompleteSupportFragment.
        // Initialize the AutocompleteSupportFragment.
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_pitch_layout, container, false)
        binding.addPitchViewModel = viewmodel

        autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment


        if (!Places.isInitialized()) {
            Places.initialize(activity!!.applicationContext, getString(R.string.google_api_key));
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )

      /*  val fields: List<Place.Field> =
            Arrays.asList(Place.Field.ID, Place.Field.NAME)

        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        ).build(requireContext())

        startActivityForResult(intent, 1)

       */

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) { // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.name + ", " + place.id + " lat :lng " + place.latLng)
            }

            override fun onError(status: Status) { // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "We are here ")
        if (requestCode === 1) {

            if (resultCode === RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                Log.i(TAG, "Place: " + place.name + ", " + place.id)
            } else if (resultCode === AutocompleteActivity.RESULT_ERROR) { // TODO: Handle the error.
                val status =
                    Autocomplete.getStatusFromIntent(data!!)
                Log.i(TAG, status.statusMessage)
            } else if (resultCode === RESULT_CANCELED) { // The user canceled the operation.
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}