package com.example.sportmatcher.service

import com.example.sportmatcher.model.notifications.NotificationType
import io.reactivex.Single

interface INotificationService {
    fun getCurrentNotificationToken():Single<String>
    fun onNewToken(token: String?)
    fun sendNotification(notificationType: NotificationType, token :String)
}