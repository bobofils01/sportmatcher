package com.example.sportmatcher.service

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FirebaseAuthService {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signIn(email : String ,password :String){

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    updateUI(null)
                }
            }
    }


    fun register(email : String ,password :String){
        
    }

    //TO DO if succeeded go to the next page if fail or disconnect dismiss everything ans show  error message
    fun updateUI(user: FirebaseUser?){
        if(user != null){

        }else{

        }
    }
}