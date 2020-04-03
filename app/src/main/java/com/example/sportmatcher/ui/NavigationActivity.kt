package com.example.sportmatcher.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.sportmatcher.R
import com.example.sportmatcher.adapters.PageAdaptater
import kotlinx.android.synthetic.main.navigation_bar_layout.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_bar_layout)

        val fragment = PageAdaptater(
            supportFragmentManager,
            4
        )
        val viewPage: ViewPager = findViewById(R.id.container)
        viewPage.adapter  = fragment
        tabs.setupWithViewPager(viewPage)
        tabs.getTabAt(0)?.setIcon(R.drawable.sport)
        tabs.getTabAt(1)?.setIcon(R.drawable.star)
        tabs.getTabAt(2)?.setIcon(R.drawable.friends)
        tabs.getTabAt(3)?.setIcon(R.drawable.settings)
    }

    override fun onBackPressed() { //Empêche l'utilisateur de se déconnecter et lui fait quitter l'application en cas de retour en arrière
        if(tabs.selectedTabPosition != 0)
            tabs.getTabAt(0)?.select()
        else {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
