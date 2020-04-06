package com.example.sportmatcher.ui.sports.session

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.getPitchesForUseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationInProgress
import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.ui.NavigationActivity

class ShowSessionViewModel : ViewModel() {

    lateinit var session:Session

    var authenticatedUser: User? = null
    val isAlreadyParticipantOfSession = MutableLiveData<Boolean>(false)


    private val getParticipantsForASessionUseCase by lazy {
        ServiceProvider.getParticipantsForASessionUseCase
    }

    private val getChatMessagesUseCase by lazy {
        ServiceProvider.getChatMessagesForASession
    }


    fun getChatMessages(session: Session):MutableLiveData<ArrayList<ChatMessage>>{
        val messages = MutableLiveData<ArrayList<ChatMessage>>()
        getChatMessagesUseCase.execute(session).subscribe {
            messages.value = it as ArrayList<ChatMessage>
        }
        return messages
    }


    fun getSessionParticipants(session: Session) : MutableLiveData<ArrayList<String>>{
        val participants = MutableLiveData<ArrayList<String>>()
        getParticipantsForASessionUseCase.execute(session).subscribe {
            var isAlreadyPart = false
            participants.value = it as ArrayList<String>
            if(it.contains(this.authenticatedUser!!.uid)){
                isAlreadyPart= true
            }
            isAlreadyParticipantOfSession.value =isAlreadyPart

        }
        return participants
    }
}
