package com.example.sportmatcher.repository

import android.util.Log
import com.example.sportmatcher.model.sport.ChatMessage
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
        private const val CHAT_PATH = "chatMessages"
        private const val PARTICIPANTS_PATH = "participants"
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

    override fun addParticipant(sessionId: String, participantId: String): Single<Boolean> {
        if (sessionId == null) {
            return Single.error(IllegalArgumentException("participant not related to a session"))
        }
        return Single.create {
                emitter ->
            sessionTableRef.child(sessionId).child(PARTICIPANTS_PATH).child(participantId).setValue(true)
            emitter.onSuccess(true)
        }
    }

    override fun removeParticipant(sessionId: String, participantId: String): Single<Boolean> {
        if (sessionId == null) {
            return Single.error(IllegalArgumentException("participant not related to a session"))
        }
        return Single.create {
                emitter ->
            sessionTableRef.child(sessionId).child(PARTICIPANTS_PATH).child(participantId).removeValue()
            emitter.onSuccess(true)
        }
    }

    override fun getParticipants(sessionId : String): Observable<List<String>>{

        return Observable.create { emitter ->
            sessionTableRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.child(sessionId).child(PARTICIPANTS_PATH).children.mapNotNull { session ->
                        session.key
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

    override fun addChatMessage(chatMessage: ChatMessage): Single<Boolean> {
        if (chatMessage?.sessionID == null) {
            return Single.error(IllegalArgumentException("Session not related to a pitch"))
        }
        return Single.create {
        emitter ->
            val key  = sessionTableRef.child(chatMessage.sessionID).child(CHAT_PATH).push().key
            chatMessage.uuid = key
            sessionTableRef.child(chatMessage.sessionID).child(CHAT_PATH).child(key!!).setValue(chatMessage.toMap())
            emitter.onSuccess(true)
        }
    }

    override fun getChatMessages(sessionId : String): Observable<List<ChatMessage>>{

        return Observable.create { emitter ->
            sessionTableRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.child(sessionId).child(CHAT_PATH).children.mapNotNull { session ->
                        session.getValue(ChatMessage::class.java)
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