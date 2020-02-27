package com.example.sportmatcher.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportmatcher.R
import com.example.sportmatcher.databinding.LoginViewBinding
import com.example.sportmatcher.viewModels.authentication.LoginViewModel

class LoginFragment : Fragment() {

    lateinit var binding: LoginViewBinding
    /**
    lateinit c'est nickel si tu vas initialiser la variable dans un constructeur ou un bloc init{}, dans le reste du code,
    c'est assez dangereux, c'est à utiliser  avec des pincettes car le compilateur ne dira rien si tu ne fais pas l'init au bon moment.
    Le onCreate est assez fiable, mais mieux vaut considérer une variable immuable et "lazy" parce que si pour une raison ou pour une autre,
    le fragment est détruit puis recréer, on va repasser par le OnCreate, donc on va réassigner un nouveau ViewModel au Fragment
    et on perdra tout l'avantage du ViewModel et du fait qu'il soit "LifeCycle Aware"
     */
    private val viewmodel: LoginViewModel by lazy {
        /**
         *
         * Renseignez-vous sur les Android Architecture Components
         *
         *
         * Explication: Même si on stocke le ViewModel dans une variable immuable,
         * on a pas la garantie que le fragment ne sera pas détruit à 100%
         * et qu'on perdra la référence mémoire du ViewModel.
         * Pour corriger ce problème, on fait appel à un ViewModelProviders
         * qui agit comme un stockage de ViewModel pour une activity/fragment donnée.
         * C'est aussi utilise pour partager un ViewModel entre une activity parent et des fragments enfants
         * https://medium.com/androiddevelopers/viewmodels-a-simple-example-ed5ac416317e
         * https://android.jlelse.eu/dive-deep-into-androids-viewmodel-android-architecture-components-e0a7ded26f70
         */
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    companion object {
        fun newInstance() = LoginFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_layout, container, false)
        binding.loginViewModel = viewmodel

        /**
         * Inutile si on on initialise le viewmodel cfr. ci-dessus, le viewmodel est déjà bindé au lifecycle actuel
         */
        //not sure
        //binding.lifecycleOwner = this

        return binding.root
    }


}