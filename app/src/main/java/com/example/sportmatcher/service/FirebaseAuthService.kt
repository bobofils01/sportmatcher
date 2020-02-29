package com.example.sportmatcher.service

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.service.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


object FirebaseAuthService {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userRepository by lazy { UserRepository }
    val currentAuthenticatedUser by lazy { MutableLiveData<User>(null) }

    fun signIn(email : String ,password :String){

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val userId = firebaseAuth.currentUser!!.uid

                    //TODO code need tobe refactored use observable a good library
                    val listener = userRepository.getUser(userId).subscribe { user ->
                        if (user.email != null) {
                            Log.d(TAG, "user connect : $user")
                            updateCurrentUser(user)
                        }
                    }


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    updateCurrentUser(null)
                    //TO DO SHOW ERROR MESSAGE
                }
            }
    }


    fun register(email : String ,password :String){
        Log.d(TAG, "init:success")
        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = firebaseAuth.currentUser!!.uid
                    //Verify Email
                    verifyEmail()

                    val createdUser =
                        User(uid = userId, email = email, firstName = email, lastName = email)
                    val user = userRepository.createUser(createdUser)
                    updateCurrentUser(user)

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


    fun forgotPassword(email: String) {
        firebaseAuth!!
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val message = "Email sent."
                    Log.d(TAG, message)
                    updateCurrentUser(null)
                } else {
                    Log.w(TAG, task.exception!!.message!!)
                }
            }
    }


    private fun verifyEmail() {
        val mUser = firebaseAuth.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "Verification email sent to " + mUser.email)
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                }
            }
    }

    //TO DO if succeeded go to the next page if fail or disconnect dismiss everything ans show  error message
    fun updateCurrentUser(user: User?) {
        if(user != null){
            Log.i("connect", " $user is connected.")
            currentAuthenticatedUser.value = user

        }else{
            currentAuthenticatedUser.value = null
        }
    }


    fun login(loginInfo: LoginInfo): LiveData<String> {
        val errorMessage = MutableLiveData<String>()

        if (isEmailValid(loginInfo.email)) {
            if (loginInfo.userPassWord.length < 8 && !isPasswordValid(loginInfo.userPassWord)) {
                errorMessage.value = "Invalid Password"
            } else {
                this.signIn(loginInfo.email, loginInfo.userPassWord)
                errorMessage.value = "Successful Login"
            }
        } else {
            errorMessage.value = "Invalid Email"
        }

        return errorMessage
    }


    fun signup(email: String, password: String, confirmPassword: String): LiveData<String> {
        val errorMessage = MutableLiveData<String>()

        if (isEmailValid(email)) {
            if (password.length < 8 && !isPasswordValid(password)) {
                errorMessage.value = "Invalid Password"
            } else if (!password.equals(confirmPassword)) {
                errorMessage.value = "unmatch password"
            } else {
                this.register(email, password)
                errorMessage.value = "Successful register"
            }
        } else {
            errorMessage.value = "Invalid Email"
        }
        return errorMessage
    }

    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val expression = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\\\S+\$).{4,}\$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}