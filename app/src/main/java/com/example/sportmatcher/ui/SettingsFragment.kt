package com.example.sportmatcher.ui


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.viewModels.authentication.LogOutViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.progress_bar_layout.view.*
import kotlinx.android.synthetic.main.settings_layout.*
import kotlinx.android.synthetic.main.signup_layout.*

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
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

        val progress = ProgressDialog(activity)
        progress.setMessage("Logging out")
        progress.setCancelable(false)
        progress.isIndeterminate = true

        val logoSize = LinearLayout.LayoutParams(100, 100)
        logoSize.setMargins(0, 0, 75, 0)
        open_door.layoutParams = logoSize

        log_out_text.setTextSize(TypedValue.COMPLEX_UNIT_SP,25F)

        log_out.setOnClickListener{
            progress.show()

            val handler = Handler()
            handler.postDelayed({run{
                viewModel.onLogoutClicked()

                startActivity(Intent(activity, WelcomeActivity::class.java).apply{
                    putExtra("LOGGING_OUT", true)
                })
            }}, 2000)
        }
    }


}
