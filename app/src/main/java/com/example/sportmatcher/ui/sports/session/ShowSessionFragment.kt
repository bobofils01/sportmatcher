package com.example.sportmatcher.ui.sports.session

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.sportmatcher.R
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.joinSessionUseCase
import com.example.sportmatcher.dto.sport.ParticipantSessionDTO
import com.example.sportmatcher.model.sport.Session
import kotlinx.android.synthetic.main.show_session_fragment.*

class ShowSessionFragment : Fragment() {

    companion object {
        private const val SESSION_KEY = "SESSION_KEY"
        fun newInstance(session : Session) :Fragment = ShowSessionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SESSION_KEY, session)
            }
        }
    }

    private lateinit var session: Session
    private val viewModel: ShowSessionViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ShowSessionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        session = arguments?.getParcelable(SESSION_KEY)!!
        viewModel.session = session
        val user = ServiceProvider.getAuthenticatedUserUseCase.execute()
        viewModel.authenticatedUser = user


        return inflater.inflate(R.layout.show_session_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSessionParticipants(session).observe(requireActivity(), Observer { participants ->
            Log.i("bobo", "participants: $participants.toString()")
        })

        btn_join_session.text = "Join"
        btn_join_session.setOnClickListener{
            joinSessionUseCase.execute(
                ParticipantSessionDTO(
                    sessionID = session.uid!!,
                    participantID = viewModel.authenticatedUser!!.uid!!
                )
            )
        }


        viewModel.isAlreadyParticipantOfSession.observe(requireActivity(), Observer { isParticipant ->
            //set the right button
            if(isParticipant){
                btn_join_session.text = "Quit"
                btn_join_session.setOnClickListener {
                    ServiceProvider.quitSessionUseCase.execute(
                        ParticipantSessionDTO(
                            sessionID = session.uid!!,
                            participantID = viewModel.authenticatedUser?.uid!!
                        )
                    ).subscribe()
                }
            }else{
                btn_join_session.text = "Join"
                btn_join_session.setOnClickListener {
                    joinSessionUseCase.execute(
                        ParticipantSessionDTO(
                            sessionID = session.uid!!,
                            participantID = viewModel.authenticatedUser?.uid!!
                        )
                    ).subscribe()
                }
            }
        })



    }

}
