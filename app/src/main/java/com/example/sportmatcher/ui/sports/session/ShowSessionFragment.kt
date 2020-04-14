package com.example.sportmatcher.ui.sports.session

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.joinSessionUseCase
import com.example.sportmatcher.dto.sport.ParticipantSessionDTO
import com.example.sportmatcher.model.sport.Session
import com.example.sportmatcher.ui.ProfileActivity
import com.example.sportmatcher.viewModels.sports.ShowSessionViewModel
import kotlinx.android.synthetic.main.show_session_fragment.*
import kotlinx.android.synthetic.main.show_session_fragment.added_players_recycle_view
import kotlinx.android.synthetic.main.show_session_fragment.session_date
import kotlinx.android.synthetic.main.show_session_fragment.session_description
import kotlinx.android.synthetic.main.show_session_fragment.session_price_player
import kotlinx.android.synthetic.main.show_session_fragment.session_time
import kotlinx.android.synthetic.main.show_session_fragment.session_title
import android.widget.AdapterView
import android.R



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


        return inflater.inflate(com.example.sportmatcher.R.layout.show_session_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initJoinButton()
        bindTextViews()
        initParticipantsList()

        added_players_recycle_view.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            startActivity(Intent(activity, ProfileActivity::class.java))
            }
    }

    private fun initParticipantsList(){
        viewModel.getSessionParticipants(session).observe(requireActivity(), Observer { playersList ->
            val emails = playersList.map { t -> t.email }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, emails)
            added_players_recycle_view.adapter = adapter

            if(session.maxNbPlayers == -1 ){
                session_max_players.text = "${emails.size}/_"
            }else if(emails.size >= session.maxNbPlayers!!){
                session_max_players.text = "${emails.size}/${session.maxNbPlayers!!}"
                join_status.text = "This session is full"
            }else{
                session_max_players.text = "${emails.size}/${session.maxNbPlayers!!}"
            }

        })
    }

    private fun bindTextViews(){
        session_title.text = session.title
        session_date.text = session.date
        session_time.text = session.time
        session_description.text = session.description
        session_price_player.text = "${session.pricePlayer} EUR"
        session_createdby.text="Created by : ${session.createdBy}"
    }

    private fun initJoinButton(){
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
                btn_join_session.setBackgroundColor(Color.RED)
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
                btn_join_session.setBackgroundResource(com.example.sportmatcher.R.drawable.button_selector)
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
