package com.example.sportmatcher.service

import android.content.ContentValues.TAG
import android.util.Log
import com.example.sportmatcher.model.notifications.NotificationType
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.Single



class FirebaseMessagingService : FirebaseMessagingService(), INotificationService{

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //TODO store notif token in the userTable or link the two
        //sendRegistrationToBackend(token)
    }
    
    override fun getCurrentNotificationToken() :Single<String>{


        //TODO this must be done once
        FirebaseMessaging.getInstance().subscribeToTopic("sportmatcher")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                   Log.i(TAG, "Invalid subscription to sportmatcher")
                }
                Log.i(TAG, "device well subscribed to sportmatcher")
            }

        return Single.create<String> { emitter ->
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast

                    Log.d(TAG, "current Token : $token")
                    emitter.onSuccess(token!!)
                })
        }
    }


    override fun sendNotification(notificationType: NotificationType, token :String){
        // The topic name can be optionally prefixed with "/topics/".
        // The topic name can be optionally prefixed with "/topics/".
        val topic = "highScores"

        Log.d(TAG, "sending notifications to Token : $token")
        // See documentation on defining a message payload.
        // See documentation on defining a message payload.



        // Send a message to the devices subscribed to the provided topic.
        // Send a message to the devices subscribed to the provided topic.
       //FirebaseMessaging.getInstance().send(message)


    }

}