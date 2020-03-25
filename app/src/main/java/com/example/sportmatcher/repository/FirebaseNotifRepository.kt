package com.example.sportmatcher.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.sportmatcher.model.User
import com.google.firebase.database.*
import io.reactivex.Observable
import java.util.ArrayList

class FirebaseNotifRepository : INotificationsRepository {

    companion object {
        private const val PATH = "notifiableDevices"
    }

    private val notificationTableRef by lazy {
        FirebaseDatabase.getInstance().getReference("/${FirebaseNotifRepository.PATH}")
    }

    override fun getNotifiableDevices() : Observable<String> {

        return Observable.create<String>{
            emitter ->
            notificationTableRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.mapNotNull{ it ->
                        it.getValue(String::class.java)?.let {
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

    override fun addNotifiableDevice(token: String?) {
        if(token != null){
            //TODO add in the database sorted by sportCategory
            val key  = notificationTableRef.push().key

            if (key == null) {
                Log.w(TAG, "Couldn't get push key for notifiableDevices")
                return
            }
            notificationTableRef.child(key).setValue(token)

        }
    }
}