package com.example.sportmatcher.ui.sports

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.ui.utils.UIUtils
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
        val user = ServiceProvider.getAuthenticatedUserUseCase.execute()
        addSessionViewModel.currentUser = user

        initTextViewChangeListeners()
        initSaveBtn()
        initPlayersSuggestionAdaptater()
        initAddPlayerBtn()


    }

    fun initAddPlayerBtn(){

    }
    fun initPlayersSuggestionAdaptater(){
        addSessionViewModel.getAllFriends().observe (this, Observer { playersList ->
            val emails = playersList.map { t -> t.email }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emails)
            friends_autoCompleteTextView.setAdapter(adapter)

        })


    }

    fun initSaveBtn(){
        //when we click to save the new session
        add_session_toolbar_save.isClickable = true
        add_session_toolbar_save.paintFlags = add_session_toolbar_save.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        add_session_toolbar_save.setOnClickListener {
            val session = addSessionViewModel.getSession()
            //TODO handle error in isSession valid (empty mandatory stuffs) )by checking
            if(isSessionValid()) {
                addSessionViewModel.onAddSessionClicked(session)
                Toast.makeText(this, "Session added", Toast.LENGTH_LONG).show()
                startActivity(AllSessionOfAPitchActivity.getIntent(this, addSessionViewModel.pitch))

            }
        }
    }
    fun  isSessionValid(): Boolean{
        return true
    }
    fun initTextViewChangeListeners(){
        //title
        UIUtils.addOnTextViewChange(session_title, addSessionViewModel.title_input)
        //description
        UIUtils.addOnTextViewChange(session_description, addSessionViewModel.description_input)
        //date
        UIUtils.addOnTextViewChange(session_date, addSessionViewModel.date_input)
        //time
        UIUtils.addOnTextViewChange(session_time, addSessionViewModel.time_input)
        //maxPlayers
        UIUtils.addOnTextViewChange(session_max_players, addSessionViewModel.maxNbPlayers_input)
        //price per player
        UIUtils.addOnTextViewChange(session_price_player, addSessionViewModel.price_player_input)

    }

}