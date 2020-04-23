package com.example.sportmatcher.ui


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.ui.friendship.FriendsFragment
import com.example.sportmatcher.viewModels.authentication.LogOutViewModel
import kotlinx.android.synthetic.main.settings_layout.*


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
        return inflater.inflate(com.example.sportmatcher.R.layout.settings_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = ServiceProvider.getAuthenticatedUserUseCase.execute()
        logoutMail.text = user!!.email
        val progress = ProgressDialog(activity)
        progress.setMessage("Logging out")
        progress.setCancelable(false)
        progress.isIndeterminate = true

        val logoSize = LinearLayout.LayoutParams(100, 100)
        logoSize.setMargins(0, 0, 75, 0)
        open_door.layoutParams = logoSize

        //friends_icon.layoutParams = logoSize

        log_out_text.setTextSize(TypedValue.COMPLEX_UNIT_SP,25F)

        //friends_text.setTextSize(TypedValue.COMPLEX_UNIT_SP,25F)

        /*friends.setOnClickListener{
            val nextFrag = FriendsFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(this.id, nextFrag, "findThisFragment")
                .addToBackStack(null).commit()
        }*/

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
