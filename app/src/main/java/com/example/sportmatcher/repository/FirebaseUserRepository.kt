package com.example.sportmatcher.repository

import com.example.sportmatcher.model.User
import com.example.sportmatcher.model.sport.Pitch
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single


class FirebaseUserRepository : IUserRepository {

    companion object {
        private const val USERS_PATH = "users"
        private const val FAV_SPORTS_COL_NAME="favouriteSports"
    }


    private val userTableRef by lazy {
        FirebaseDatabase.getInstance().getReference("/$USERS_PATH")
    }

    override fun createUser(user: User): Single<User> {
        if (user.uid == null) {
            return Single.error(IllegalArgumentException("useruuid is null"))
        }
        return Single.create { emitter ->
            userTableRef.child(user.uid!!).setValue(user.toMap())
            emitter.onSuccess(user)
        }
    }

    override fun updateUser(user: User): Single<User> {
        return Single.just(user)
    }

    override fun getUser(uid: String): Single<User> {
        return Single.create { emitter ->
            userTableRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.getValue(User::class.java)?.let {
                        emitter.onSuccess(it)
                    } ?: emitter.onError(IllegalStateException("Value shouldn't be null"))
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onError(
                        databaseError.toException()
                            ?: IllegalStateException("Firebase error received")
                    )
                }
            })

        }
    }

    override fun getUserSportFavourite(uid: String): Single<ArrayList<String>> {
        return Single.create { emitter ->
            userTableRef.child(uid).child(FAV_SPORTS_COL_NAME).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.children.mapNotNull {pitch ->
                        pitch.key
                    }
                    emitter.onSuccess(ArrayList(list))
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onError(
                        databaseError.toException()
                            ?: IllegalStateException("Firebase error received getting favourite sports")
                    )
                }
            })
        }

    }

    override fun updateUserSportFavourite(uid: String, list: ArrayList<String>) {
        userTableRef.child(uid).child(FAV_SPORTS_COL_NAME).setValue("")
        for (sport in list){
            userTableRef.child(uid).child(FAV_SPORTS_COL_NAME).child(sport).setValue(true)
        }

    }
}