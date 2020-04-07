package com.example.sportmatcher.ui.sports

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.FriendListAdapter
import com.example.sportmatcher.adapters.PlayersListAdapter
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.ui.utils.UIUtils
import com.example.sportmatcher.viewModels.sports.AddSessionViewModel
import kotlinx.android.synthetic.main.add_session_layout.*
import java.util.*

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
        addSessionViewModel.addPlayer(user!!)
        initTextViewChangeListeners()
        initSaveBtn()
        initPlayersSuggestionAdaptater()
        initAddPlayerBtn()
        initPlayersListView()
        initDateTimePicker()



    }

    fun initDateTimePicker(){

        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        var hour  =  c.get(Calendar.HOUR_OF_DAY)
        var minutes = c.get(Calendar.MINUTE)

        session_date.setOnClickListener{
            val date = session_date.text.toString()
            if(!date.isNullOrBlank()){
                val spr = date.split("/")
                day = spr[0].toInt()
                month = spr[1].toInt()
                year = spr[2].toInt()
            }
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                session_date.setText("" + dayOfMonth + "/" + month + "/" + year)
            }, year, month, day)
            dpd.show()
        }


        session_time.setOnClickListener {
            val time = session_time.text.toString()
            if(!time.isNullOrBlank()){
                val spr = time.split(":")
                hour = spr[0].toInt()
                minutes = spr[1].toInt()
            }
            val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                session_time.setText(""+hourOfDay+":"+minute)
            }, hour, minutes,true)
            tpd.show()
        }
    }

    fun initPlayersListView(){
        addSessionViewModel.players.observe(this, Observer { players ->
            friends_autoCompleteTextView.setTextColor(Color.BLACK)
            val adapter = PlayersListAdapter(players, addSessionViewModel.currentUser!!){ userToDelete ->
                addSessionViewModel.deletePlayer(user = userToDelete)
            }
            added_players_recycle_view.layoutManager=  LinearLayoutManager(this)
            added_players_recycle_view.adapter = adapter

        })
    }

    fun initPlayersSuggestionAdaptater(){

        addSessionViewModel.getAllFriends().observe (this, Observer { playersList ->
            val emails = playersList.map { t -> t.email }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emails)
            friends_autoCompleteTextView.threshold = 0
            friends_autoCompleteTextView.setAdapter(adapter)
        })

        friends_autoCompleteTextView.setOnClickListener {
            friends_autoCompleteTextView.showDropDown()
        }

    }

    fun initAddPlayerBtn() {

        add_player_btn.setOnClickListener {
            val playerText = friends_autoCompleteTextView.text
            addSessionViewModel.getAllFriends().observe(this, Observer { friends ->
                var isAfriend: User? = null
                for (p in friends) {
                    if (p.email.equals(playerText.toString())){
                        isAfriend = p
                    }
                }
                if (isAfriend != null && !addSessionViewModel.players.value!!.contains(isAfriend)) {
                    addSessionViewModel.addPlayer(isAfriend)
                    friends_autoCompleteTextView.setText("")
                } else {
                    friends_autoCompleteTextView.setTextColor(Color.RED)
                }
            })
        }
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