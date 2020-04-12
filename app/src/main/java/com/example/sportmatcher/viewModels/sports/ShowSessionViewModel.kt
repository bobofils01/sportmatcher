package com.example.sportmatcher.viewModels.sports

import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.viewModels.AbstractViewModel

class ShowSessionViewModel : AbstractViewModel() {

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
        compositeDisposable.add(
            getChatMessagesUseCase.execute(session).subscribe {
                messages.value = it as ArrayList<ChatMessage>
            }
        )
        return messages
    }


    fun getSessionParticipants(session: Session) : MutableLiveData<ArrayList<User>>{
        val participants = MutableLiveData<ArrayList<User>>()
        compositeDisposable.add(
            getParticipantsForASessionUseCase.execute(session).subscribe {
                var isAlreadyPart = false
                participants.value = it as ArrayList<User>

                if(it.map {t -> t.uid}.contains(this.authenticatedUser!!.uid)){
                    isAlreadyPart= true
                }
                isAlreadyParticipantOfSession.value =isAlreadyPart

            }
        )
        return participants
    }
}
