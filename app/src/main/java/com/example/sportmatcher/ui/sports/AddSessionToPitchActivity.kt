package com.example.sportmatcher.ui.sports

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.ui.authentication.LoginViewState
import com.example.sportmatcher.viewModels.sports.AddSessionViewModel
import kotlinx.android.synthetic.main.add_session_layout.*

class AddSessionToPitchActivity : AppCompatActivity() {

    //lateinit var binding: AddSessionViewBinding

    companion object {

        private const val PITCH_KEY = "pitch_key"

        fun getIntent(context: Context, pitch: Pitch): Intent {
            return Intent(context, AddSessionToPitchActivity::class.java).apply {
                putExtra(PITCH_KEY, pitch)
            }
        }
    }

    private val addSessionViewModel : AddSessionViewModel by lazy {
        ViewModelProvider(this).get(AddSessionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_session_layout)
        //val binding: AddSessionViewBinding = DataBindingUtil.setContentView(this, R.layout.add_session_layout)
        //binding = DataBindingUtil.inflate(layoutInflater, R.layout.add_session_layout, null, true)
        //binding.addSessionViewModel = addSessionViewModel
        //setContentView(binding.root);
        //binding.lifecycleOwner = this
        addSessionViewModel.pitch = intent.extras?.get(PITCH_KEY) as Pitch

        editTextTotalNbPlayersID.addTextChangedListener (object :TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("ASTPAbeforeTextChanged", editTextTotalNbPlayersID.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrBlank()) {
                    val intRead = p0.toString().toInt()
                    addSessionViewModel.totalNbPlayers.value = intRead

                }
                Log.d("ASTPAafterTextChanged", p0.toString() + addSessionViewModel.totalNbPlayers.value.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("ASTPAonTextChanged", editTextTotalNbPlayersID.text.toString()+" "+ p0.toString()+" "+ p1.toString()+" "+ p2.toString()+" "+ p3.toString())
            }
        })

        editTextNbPlayersSignedID.addTextChangedListener (object :TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrBlank()) {
                    val intRead = p0.toString().toInt()
                    addSessionViewModel.nbPlayersSigned.value = intRead
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        editTextPriceSessionID.addTextChangedListener (object :TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrBlank()) {
                    val doubleRead = p0.toString().toDouble()
                    addSessionViewModel.price.value = doubleRead
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        addSessionBtn.setOnClickListener {
            addSessionViewModel.onAddSessionClicked()
        }

    }

}