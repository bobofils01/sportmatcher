package com.example.sportmatcher.ui.sports

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
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
import java.lang.invoke.MethodHandles
import java.util.*

class AddPitchViewFragment: Fragment(), PlaceSelectionListener {

    companion object {
        private const val EXTRA_VILLE = "extraVille"
        fun newInstance(ville:String):Fragment{
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

        val autocompleteFragment = activity!!.
            supportFragmentManager.
            findFragmentById(R.id.autocomplete_fragment) as? AutocompleteSupportFragment

        if (!Places.isInitialized()) {
            Places.initialize(activity!!.applicationContext, getString(R.string.google_api_key));
        }
        // Specify the types of place data to return.
        // Specify the types of place data to return.
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment?.setOnPlaceSelectedListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // listen to buttons

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

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 200){
            val selectedImage: Uri? = data?.data
            imageViewPhotoPitch.setImageURI(selectedImage)
        }
    }
    */
    override fun onActivityResult(p0: Int, p1: Int, p2: Intent?) {
        super.onActivityResult(p0, p1, p2)

    }

    override fun onPlaceSelected(place: Place) {
        Log.d("TAG", "Place: " + place.name + ", " + place.id)
    }

    override fun onError(p0: Status) {
        Log.d("TAG", "An error occurred: $p0")
    }
}