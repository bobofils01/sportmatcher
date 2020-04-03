package com.example.sportmatcher.repository

import com.example.sportmatcher.dto.friendship.InvitationDTO
import com.example.sportmatcher.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single


class FirebaseUserRepository : IUserRepository {

    companion object {
        private const val USERS_PATH = "users"
        private const val FAV_SPORTS_COL_NAME = "favouriteSports"
        private const val INVITATIONS_SENT_PATH = "invitations_sent"
        private const val INVITATIONS_RECEIVED_PATH = "invitations_received"
        private const val FRIENDS_PATH = "friends"
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

    override fun getAllUser(): Observable<List<User>> {
        return Observable.create { emitter ->
            userTableRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.children.mapNotNull { user ->
                        user.getValue(User::class.java)
                    }
                    emitter.onNext(list)
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

    override fun sendAndReceiveInvitation(invitation: InvitationDTO){
        if (invitation.from.uid.isNullOrBlank()) {
            return error(IllegalArgumentException("sender is null"))
        } else if (invitation.to.uid.isNullOrBlank()) {
            return error(IllegalArgumentException("receiver is null"))
        }
        invitation.from.friends = null
        invitation.to.friends =null
        userTableRef.child(invitation.from.uid!!).child(FRIENDS_PATH).child(invitation.to.uid!!).setValue(invitation.to.toMap())
        userTableRef.child(invitation.to.uid!!).child(FRIENDS_PATH).child(invitation.from.uid!!).setValue(invitation.from.toMap())
    }

    override fun getAllFriends(uid: String): Observable<List<User>> {
        return Observable.create { emitter ->
            userTableRef.child(uid).child(FRIENDS_PATH).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.children.mapNotNull { user ->
                        user.getValue(User::class.java)
                    }
                    emitter.onNext(list)
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

    override fun deleteFriend(deletion: InvitationDTO) {
        if (deletion.from.uid.isNullOrBlank()) {
            return error(IllegalArgumentException("sender is null"))
        } else if (deletion.to.uid.isNullOrBlank()) {
            return error(IllegalArgumentException("receiver is null"))
        }
        userTableRef.child(deletion.from.uid!!).child(FRIENDS_PATH).child(deletion.to.uid!!).removeValue()
        userTableRef.child(deletion.to.uid!!).child(FRIENDS_PATH).child(deletion.from.uid!!).removeValue()
    }
    /*override fun receiveInvitation(invitation: Invitation) {
        if (invitation.from.isNullOrBlank()) {
            return error(IllegalArgumentException("sender is null"))
        } else if (invitation.to.isNullOrBlank()) {
            return error(IllegalArgumentException("receiver is null"))
        }
        userTableRef.child(invitation.to!!).child(INVITATIONS_RECEIVED_PATH).child(invitation.from!!).setValue(true)
    }

    override fun sendAndReceiveInvitation(invitation: Invitation): Single<User> {
        if (invitation.from.isNullOrBlank()) {
            return Single.error(IllegalArgumentException("sender is null"))
        }
        else if(invitation.to.isNullOrBlank()){
            return Single.error(IllegalArgumentException("receiver is null"))
        }
        return Single.create { emitter ->
            /*userTableRef.child(from.uid!!).child(INVITATIONS_SENT_PATH).child(to).setValue(true)
            emitter.onSuccess(user)
             */
            userTableRef.runBatch{transaction ->
                val snapshot = transaction.get(sfDocRef)
            }
        }

    }*/
}
