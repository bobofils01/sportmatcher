package com.example.sportmatcher.viewModels.sports


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.getFriendsUseCase
import com.example.sportmatcher.dto.sport.ParticipantSessionDTO
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session

class AddSessionViewModel: ViewModel() {

    val title_input by lazy { MutableLiveData<String>("") }
    val date_input by lazy { MutableLiveData<String>("") }
    val time_input by lazy { MutableLiveData<String>("") }
    val description_input by lazy {MutableLiveData<String>("")}
    val maxNbPlayers_input by lazy {MutableLiveData<String>("")}
    val price_player_input by lazy {MutableLiveData<String>("")}
    val players = ArrayList<User>()
    fun getSession() : Session{
        val title = title_input.value
        val date = date_input.value
        val time = time_input.value
        val description = description_input.value
        val maxNumberPlayers = if(!maxNbPlayers_input.value.isNullOrBlank())  maxNbPlayers_input.value?.toInt() else -1
        val pricePlayer = if(!price_player_input.value.isNullOrBlank())  price_player_input.value?.toDouble() else 0.0

        return Session(
            pitch=pitch.uid,
            title = title,
            description = description,
            time = time,
            date = date,
            maxNbPlayers = maxNumberPlayers,
            pricePlayer = pricePlayer
        )
    }

    lateinit var pitch: Pitch

    var currentUser:User? = null

    private val addSessionUseCase by lazy {
        ServiceProvider.addSessionUseCase
    }

    private val joinSessionUseCase by lazy {
        ServiceProvider.joinSessionUseCase
    }


    fun onAddSessionClicked(session: Session) {
        addSessionUseCase.execute(session).subscribe{ addedSession ->
            //TODO add in players the current useruuid and the listof players ids
            for(player in players){
                joinSessionUseCase.execute(
                    ParticipantSessionDTO(sessionID = addedSession.uid!!, participantID = player.uid!!)
                ).subscribe()
            }
        }

    }

    fun getAllFriends(): MutableLiveData<ArrayList<User>> {

        val userUuid = currentUser!!.uid!!
        val friendsMutableData = MutableLiveData<ArrayList<User>>()
        getFriendsUseCase.execute(userUuid).subscribe{
            friendsMutableData.value = it as ArrayList<User>
        }
        return friendsMutableData
    }

}