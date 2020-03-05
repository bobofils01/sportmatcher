package com.example.sportmatcher.repository

import android.util.Log
import com.example.sportmatcher.model.sport.Pitch
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single


class FirebasePitchesRepository : IPitchesRepository {

    companion object {
        private const val PITCHES_PATH = "pitches"
    }

    private val pitchesTableRef by lazy {
        FirebaseDatabase.getInstance().getReference("/${FirebasePitchesRepository.PITCHES_PATH}")
    }

    override fun addPitch(pitch: Pitch): Single<Pitch> {
        if (pitch.address.isNullOrBlank()) {
            return Single.error(IllegalArgumentException("pitch address null or blank"))
        }
        val key  = pitchesTableRef.push().key
        pitch.uid = key
        Log.d("ONADDPITCH", "Before")
        return Single.create { emitter ->
            Log.d("ONADDPITCH", "OK")
            pitchesTableRef.child(key!!).setValue(pitch.toMap())
            emitter.onSuccess(pitch)
        }
    }

    override fun updatePitch(pitch: Pitch): Single<Pitch> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllPitches(): Observable<Pitch> {
        return Observable.create { emitter ->
            pitchesTableRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.mapNotNull {pitch ->
                        /*var p = pitch.getValue(true)
                        Log.d("WESH ", p.toString())
                        */
                        pitch.getValue(Pitch::class.java)?.let {
                            emitter.onNext(it)
                        }
                    }
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