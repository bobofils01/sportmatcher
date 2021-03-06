package com.example.sportmatcher.repository

import android.util.Log
import com.example.sportmatcher.dto.sport.AddSessionToPitchDTO
import com.example.sportmatcher.model.sport.Pitch
import com.example.sportmatcher.model.sport.Session
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single


class FirebasePitchesRepository : IPitchesRepository {

    companion object {
        private const val PITCHES_PATH = "pitches"
        private const val SESSIONS_IN_PITCH_PATH = "sessions"
    }

    private val pitchesTableRef by lazy {
        FirebaseDatabase.getInstance().getReference("/$PITCHES_PATH")
    }

    override fun addPitch(pitch: Pitch): Single<Pitch> {
        if (pitch.address.isNullOrBlank()) {
            return Single.error(IllegalArgumentException("pitch address null or blank"))
        }
        val key  = pitchesTableRef.push().key
        pitch.uid = key
        return Single.create { emitter ->
            pitchesTableRef.child(key!!).setValue(pitch.toMap())
            emitter.onSuccess(pitch)
        }
    }

    override fun updatePitch(pitch: Pitch): Single<Pitch> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllPitches(): Observable<List<Pitch>> {
        return Observable.create { emitter ->
            pitchesTableRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.children.mapNotNull {pitch ->
                        pitch.getValue(Pitch::class.java)
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

    override fun getPitch(uid: String): Single<Pitch> {
        return Single.create { emitter ->
            pitchesTableRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.getValue(Pitch::class.java)?.let {
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

    override fun addSessionToPitch(addSessionToPitchDTO: AddSessionToPitchDTO): Single<Pitch> {
        return Single.create { emitter ->
            pitchesTableRef.child(addSessionToPitchDTO.pitchId).child(SESSIONS_IN_PITCH_PATH).child(addSessionToPitchDTO.sessionId).setValue(true)
            pitchesTableRef.child(addSessionToPitchDTO.pitchId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    dataSnapshot.getValue(Pitch::class.java)?.let {
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


    override fun getAllSessionsForAPitch(uid: String): Observable<List<String>> {
        return Observable.create { emitter ->
            pitchesTableRef.child(uid).child(SESSIONS_IN_PITCH_PATH).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val keys = dataSnapshot.children.mapNotNull { session ->
                        session.key
                    }
                    Log.d("FIREBASEKEY", keys.toString())

                    emitter.onNext(keys)
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