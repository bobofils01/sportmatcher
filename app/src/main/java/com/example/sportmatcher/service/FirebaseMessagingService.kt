package com.example.sportmatcher.service

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.sportmatcher.R
import com.example.sportmatcher.model.notifications.NotificationType
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.Single


class FirebaseMessagingService : FirebaseMessagingService(), INotificationService{
    companion object{
        private const val SPORT_TOPIC_PREFIX = "TOPIC_SPORT_"
    }
    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //TODO resubscribe to topics
        //sendRegistrationToBackend(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // whe we receive the message in foreground we show it.
        toastNotificatinForeground(remoteMessage.notification?.body!!)
    }

    private  fun toastNotificatinForeground(message: String){
        //handler to be able to show the toast on the ui thread.
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable {

            val layout = LinearLayout(applicationContext)
            layout.setBackgroundResource(R.drawable.button)
            layout.orientation= LinearLayout.HORIZONTAL
            layout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)


            val tv = TextView(applicationContext)
            tv.setTextColor(Color.WHITE)
            tv.text = message
            tv.textSize = 20F


            val img = ImageView(applicationContext)
            img.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            img.setImageResource(R.mipmap.ic_launcher)


            layout.addView(img)
            layout.addView(tv)
            val toast = Toast(applicationContext)

            toast.view = layout
            toast.duration = Toast.LENGTH_LONG

            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0,0)
            toast.show()



        })
    }

    override fun getCurrentNotificationToken() :Single<String>{

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

    override fun subscribeToSportTopic( sportTopics: ArrayList<String>){
        //TODO this must be done once
        for (sportTopic in sportTopics){
            FirebaseMessaging.getInstance().subscribeToTopic(SPORT_TOPIC_PREFIX+sportTopic)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.i(TAG, "Invalid subscription to $sportTopic")
                    }
                    Log.i(TAG, "device well subscribed to $sportTopic")
                }
        }

    }

    override fun unsubscribeFromSportTopic( sportTopics: ArrayList<String>){
        //TODO this must be done once
        for (sportTopic in sportTopics){
            FirebaseMessaging.getInstance().unsubscribeFromTopic(SPORT_TOPIC_PREFIX+sportTopic)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.i(TAG, "Invalid unsubscription to $sportTopic")
                    }
                    Log.i(TAG, "device well unsubscribed to $sportTopic")
                }
        }

    }

}