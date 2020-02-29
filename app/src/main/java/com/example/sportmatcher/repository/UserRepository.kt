package com.example.sportmatcher.service.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable


object UserRepository {

    private val userTableRef by lazy {
        FirebaseDatabase.getInstance().getReference("/users")
    }

    fun createUser(user: User): User {
        if (user.uid == null) {
            throw IllegalArgumentException("useruuid is null")
        }

        userTableRef.child(user.uid!!).setValue(user.toMap())
        Log.i("t", "create user succeeded : $user")

        return user
    }

    fun updateUser(user: User): User? {

        return user
    }

    fun getUser(uid: String): Observable<User> {

        var user = MutableLiveData<User>()
        //TODO find a way to create a good observable
        var us: Observable<User> = Observable.just(User())
        userTableRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user.value = dataSnapshot.getValue(User::class.java)
                Log.i("t", "signin user $user")
                us = Observable.just(user.value)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        return us
    }
}