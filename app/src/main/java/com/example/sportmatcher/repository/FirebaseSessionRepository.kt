package com.example.sportmatcher.repository

import com.example.sportmatcher.model.sport.Session
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single

class FirebaseSessionRepository: ISessionRepository {

    companion object {
        private const val SESSION_PATH = "sessions"
    }

    private val sessionTableRef by lazy {
        FirebaseDatabase.getInstance().getReference("/$SESSION_PATH")
    }
    override fun addSession(session: Session): Single<Session> {
        if (session.pitch == null) {
            return Single.error(IllegalArgumentException("Session not related to a pitch"))
        }
        val key  = sessionTableRef.push().key
        session.uid = key
        return Single.create { emitter ->
            sessionTableRef.child(key!!).setValue(session.toMap())
            emitter.onSuccess(session)
        }
    }

    override fun updateSession(session: Session): Single<Session> {
        if (session.uid == null) {
            return Single.error(IllegalArgumentException("Session without uid"))
        }
        val key = session.uid!!
        val mapUpdates = HashMap<String, Any>()
        mapUpdates[key] = session
        return Single.create { emitter ->
            sessionTableRef.updateChildren(mapUpdates)
            emitter.onSuccess(session)
        }
    }

    override fun getAllSessions(): Observable<List<Session>> {
        return Observable.create { emitter ->
            sessionTableRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.children.mapNotNull { session ->
                        session.getValue(Session::class.java)
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

    override fun getSession(uid: String): Single<Session> {
        return Single.create { emitter ->
            sessionTableRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.getValue(Session::class.java)?.let {
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
}