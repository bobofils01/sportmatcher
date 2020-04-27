package com.example.sportmatcher.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.ui.sports.SportHomePageActivity
import kotlinx.android.synthetic.main.sport_choice_layout.*
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView



/**
 * A simple [Fragment] subclass.
 */
class SportChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.sportmatcher.R.layout.sport_choice_layout, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Liste contenant les logos utilisés pour chaque sport
        //TODO mettre les images dans la database
        val sportsLogo = ArrayList<Int>()
        sportsLogo.add(com.example.sportmatcher.R.drawable.basketball)
        sportsLogo.add(com.example.sportmatcher.R.drawable.soccer)
        sportsLogo.add(com.example.sportmatcher.R.drawable.tennis)

        /*setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)*/

        ServiceProvider.getAllSportsUseCase.execute().subscribe{ sports ->
            sports_choice_list.removeAllViews()

            //Indice pour la liste des logos
            var sportAt: Int = 0

            for(sport in sports){

                val choice = LinearLayout(activity)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(25, 50, 0, 50)
                choice.layoutParams = params
                choice.orientation = LinearLayout.HORIZONTAL
                choice.isClickable = true

                val logo = ImageView(activity)
                logo.setImageResource(sportsLogo[sportAt])
                val logoSize = LinearLayout.LayoutParams(100, 100)
                logoSize.setMargins(0, 0, 75, 0)
                logo.layoutParams = logoSize
                choice.addView(logo)

                val sportName = TextView(activity)
                sportName.text = sport.toUpperCase()
                sportName.setTextSize(TypedValue.COMPLEX_UNIT_SP,25F)
                sportName.setTypeface(null, Typeface.BOLD)
                choice.addView(sportName)

                choice.setOnClickListener{
                        it -> onSportClick(sportName.text.toString())
                }

                sports_choice_list.addView(choice)

                val split = LinearLayout(activity)
                val splitParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                splitParams.setMargins(0, 0, 0, 0)
                /*choice.layoutParams = params
                choice.orientation = LinearLayout.HORIZONTAL*/

                val line = View(activity)
                line.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5)
                line.setBackgroundResource(com.example.sportmatcher.R.color.colorGrey)
                split.addView(line)

                sports_choice_list.addView(split)

                sportAt = sportAt.inc()

                /*val btn_sport = Button(activity)
                btn_sport.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(82))
                btn_sport.text = sport.toUpperCase()
                //btn_sport.background = ResourcesCompat.getDrawable(resources, R.drawable.yours, null)
                btn_sport.setOnClickListener{
                        it -> onSportClick(it)
                }

                sports_choice_list.addView(btn_sport)*/
            }
        }

    }

    private fun onSportClick(sportName: String) {
        val intent = Intent(activity, SportHomePageActivity::class.java).apply {
            putExtra("SPORT_NAME", sportName)
        }
        startActivity(intent)
    }
}
