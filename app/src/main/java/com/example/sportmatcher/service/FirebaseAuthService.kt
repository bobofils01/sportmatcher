package com.example.sportmatcher.service

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FirebaseAuthService {

    private val firebaseAuth = FirebaseAuth.getInstance()

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("/sportmatcher-41fe1/users")

    fun signIn(email : String ,password :String){

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = myRef.child(firebaseAuth.currentUser!!.uid).key
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    updateUI(null)
                    //TO DO SHOW ERROR MESSAGE
                }
            }
    }


    fun register(email : String ,password :String){
        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = firebaseAuth.currentUser!!.uid
                    //Verify Email
                    verifyEmail();
                    //update user profile information
                    val currentUserDb = myRef.child(userId)


                    updateUI(currentUserDb.key)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    //TO DO SHOW ERROR MESSAGE
                }
            }
    }


    fun logout(){
        firebaseAuth.signOut()
    }


    fun forgotPassword(email: String){
        firebaseAuth!!
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val message = "Email sent."
                    Log.d(TAG, message)
                    updateUI(null)
                } else {
                    Log.w(TAG, task.exception!!.message)
                }
            }
    }


    private fun verifyEmail() {
        val mUser = firebaseAuth.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e(TAG,  "Verification email sent to " + mUser.getEmail())
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                }
            }
    }

    //TO DO if succeeded go to the next page if fail or disconnect dismiss everything ans show  error message
    fun updateUI(user: String?){
        if(user != null){
            Log.i("connect", user+" is connected.")
        }else{

        }
    }
}