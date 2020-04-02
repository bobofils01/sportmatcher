package com.example.sportmatcher.ui.sports.session

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.PitchesListAdapter
import com.example.sportmatcher.adapters.SessionChatListAdapter
import com.example.sportmatcher.di.ServiceProvider.getAuthenticatedState
import com.example.sportmatcher.di.ServiceProvider.sendMessageUseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.sport.ChatMessage
import com.example.sportmatcher.model.sport.Session
import kotlinx.android.synthetic.main.all_sports_empty_view_layout.*
import kotlinx.android.synthetic.main.session_chat_fragment.*

class SessionChatFragment : Fragment() {

    companion object {
        private const val SESSION_KEY = "SESSION_KEY"
        fun newInstance(session : Session) :Fragment = SessionChatFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SESSION_KEY, session)
            }
        }
    }

    private lateinit var session: Session
    private val viewModel: ShowSessionViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ShowSessionViewModel::class.java)
    }

    private lateinit var currentUser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        session = arguments?.getParcelable(SESSION_KEY)!!
        val view = inflater.inflate(R.layout.session_chat_fragment, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            //listerner when we send a message
            btn_send_chat.setOnClickListener{
                val msg = editTextChat.text
                if(!msg.isNullOrBlank()) {
                    val chatMessage = ChatMessage(
                        uuid = null,
                        senderUUID = viewModel.getAuthenticatedUser()?.uid!!,
                        senderName = viewModel.getAuthenticatedUser()?.lastName!!,
                        timestamp = System.currentTimeMillis()/1000,
                        message = msg.toString(),
                        sessionID = session.uid
                    )
                    sendMessageUseCase.execute(chatMessage).subscribe{ answr  ->
                        if(answr){
                            editTextChat.text.clear()
                        }
                    }
                }
            }


        //listen to chat messages
        viewModel.getChatMessages(session).observe(requireActivity(), Observer{ messages ->
            context?.let {
                val adapter = SessionChatListAdapter(messages, requireContext())
                recycleViewChat.adapter =  adapter
            }
        })

    }

}
