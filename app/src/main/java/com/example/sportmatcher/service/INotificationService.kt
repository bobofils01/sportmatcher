package com.example.sportmatcher.service

import com.example.sportmatcher.model.notifications.NotificationType
import io.reactivex.Single

interface INotificationService {
    fun getCurrentNotificationToken():Single<String>
    fun onNewToken(token: String?)
    fun subscribeToSportTopic( sportTopics: ArrayList<String>)
    fun unsubscribeFromSportTopic( sportTopics: ArrayList<String>)
}