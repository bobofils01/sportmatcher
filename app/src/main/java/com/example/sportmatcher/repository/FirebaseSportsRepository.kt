package com.example.sportmatcher.repository

import com.example.sportmatcher.model.sport.Pitch
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable

class  FirebaseSportsRepository : ISportsRepository{
    companion object {
        private const val SPORT_PATH = "sports"
        private const val PITCHES_SPORT=" pitches"
    }

    private val SportTableRef by lazy {
        FirebaseDatabase.getInstance().getReference("/$SPORT_PATH")
    }

    override fun getAllSports(): Observable<ArrayList<String>> {
        return Observable.create { emitter ->
            SportTableRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val list = dataSnapshot.children.mapNotNull { session ->
                        session.key
                    }
                    emitter.onNext(ArrayList(list))
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

    override fun updateAllSports( list : ArrayList<String>){
        SportTableRef.setValue("")
        for (sport in list){
            SportTableRef.child(sport).setValue(true)
        }
    }

    override fun addSport(sport : String){
        SportTableRef.child(sport).setValue(true)
    }

    override fun addPitchToSport( pitch : Pitch){
        SportTableRef.child(pitch.sport!!.toLowerCase()).child(PITCHES_SPORT).child(pitch.uid!!).setValue(true)
    }

    override fun getPitchesOfSport(sportID: String): Observable<ArrayList<String>> {
        return Observable.create { emitter ->
            SportTableRef.child(sportID.toLowerCase()).child(PITCHES_SPORT)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val list = dataSnapshot.children.mapNotNull { session ->
                            session.key
                        }
                        emitter.onNext(ArrayList(list))
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