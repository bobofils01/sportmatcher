package com.example.sportmatcher.viewModels.sports


import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.getFriendsUseCase
import com.example.sportmatcher.dto.sport.ParticipantSessionDTO
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.viewModels.AbstractViewModel

class AddSessionViewModel: AbstractViewModel() {

    val title_input by lazy { MutableLiveData<String>("") }
    val date_input by lazy { MutableLiveData<String>("") }
    val time_input by lazy { MutableLiveData<String>("") }
    val description_input by lazy {MutableLiveData<String>("")}
    val maxNbPlayers_input by lazy {MutableLiveData<String>("")}
    val price_player_input by lazy {MutableLiveData<String>("")}
    val players  by lazy {
        MutableLiveData<ArrayList<User>>(ArrayList())
    }
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
            pricePlayer = pricePlayer,
            createdBy = currentUser?.lastName
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
        compositeDisposable.add(
            addSessionUseCase.execute(session).subscribe{ addedSession ->
                //TODO add in players the current useruuid and the listof players ids
                for(player in players.value!!){
                    joinSessionUseCase.execute(
                        ParticipantSessionDTO(sessionID = addedSession.uid!!, participantID = player.uid!!)
                    ).subscribe()
                }
            }
        )

    }

    fun addPlayer( user : User){
        players.value!!.add(user)
        players.value = players.value
    }

    fun deletePlayer( user : User){
        players.value!!.remove(user)
        players.value = players.value
    }
    fun getAllFriends(): MutableLiveData<ArrayList<User>> {

        val userUuid = currentUser!!.uid!!
        val friendsMutableData = MutableLiveData<ArrayList<User>>()
        compositeDisposable.add(
            getFriendsUseCase.execute(userUuid).subscribe{
                friendsMutableData.value = it as ArrayList<User>
            }
        )
        return friendsMutableData
    }

}