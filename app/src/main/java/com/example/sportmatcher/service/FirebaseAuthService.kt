package com.example.sportmatcher.service

import android.content.ContentValues.TAG
import android.util.Log
import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.NotAuthenticated
import com.example.sportmatcher.repository.FirebaseUserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.*
import io.reactivex.subjects.BehaviorSubject


class FirebaseAuthService : IAuthService {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userRepository by lazy { FirebaseUserRepository() }
    private val subject by lazy {
        BehaviorSubject.create<AuthenticationState>()
    }

    override fun getCurrentUser() :Observable<String>{

        return Observable.create{
                emitter ->
                firebaseAuth.addAuthStateListener { firebaseAuth ->
                    if(firebaseAuth.currentUser != null){
                        emitter.onNext(firebaseAuth.currentUser!!.uid)
                    }
            }

        }
    }

    override fun signIn(email: String, password: String): Single<AuthenticationState> {
        return Single.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val userId = firebaseAuth.currentUser!!.uid


                        userRepository.getUser(userId).subscribe({ user ->
                            Log.d(TAG, "user connect : $user")
                            updateCurrentUser(user, emitter)
                        }
                        ) { throwable ->
                            Log.e(TAG, "error ", throwable)
                            updateCurrentUser(emit = emitter)
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        updateCurrentUser(emit = emitter)
                        //TODO SHOW ERROR MESSAGE
                    }
                }
        }
    }


    override fun register(email: String, password: String): Single<AuthenticationState> {
        return Single.create { emitter ->
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
                        userRepository.createUser(createdUser).subscribe({ user ->
                            Log.d(TAG, "user registered : $user")
                            updateCurrentUser(user, emitter)
                        }) { throwable ->
                            Log.e(TAG, "error ", throwable)
                            updateCurrentUser(emit = emitter)
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        //TO DO SHOW ERROR MESSAGE
                        updateCurrentUser(emit = emitter)
                    }
                }
        }
    }


    override fun logout(): Single<AuthenticationState> {
        return Single.create { emitter ->
            firebaseAuth.signOut()
            updateCurrentUser(emit = emitter)
        }
    }


    override fun forgotPassword(email: String) {
        firebaseAuth!!
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val message = "Email sent."
                    Log.d(TAG, message)
                    updateCurrentUser(null, null)
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
    private fun updateCurrentUser(
        user: User? = null,
        emit: SingleEmitter<AuthenticationState>? = null
    ) {
        if (user != null) {
            Log.i("connect", " $user is connected.")
            val auth = AuthenticatedState(user)
            subject.onNext(auth)
            emit?.onSuccess(auth)
        } else {
            subject.onNext(NotAuthenticated)
            emit?.onSuccess(NotAuthenticated)
        }
    }

    override fun getAuthenticationState(): Observable<AuthenticationState> {
        return subject
    }


}