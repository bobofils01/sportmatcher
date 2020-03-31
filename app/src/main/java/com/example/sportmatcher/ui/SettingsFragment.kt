package com.example.sportmatcher.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.viewModels.authentication.LogOutViewModel
import kotlinx.android.synthetic.main.settings_layout.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    private val viewModel: LogOutViewModel by lazy {
        ViewModelProvider(this).get(LogOutViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log_out.setOnClickListener{
            viewModel.onLogoutClicked()
            Toast.makeText(activity,"Logged out",Toast.LENGTH_LONG).show()
            startActivity(Intent(activity, WelcomeActivity::class.java))
        }
    }


}
