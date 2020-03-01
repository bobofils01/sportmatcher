package com.example.sportmatcher.domain.notifications

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.notifications.NotificationType
import com.example.sportmatcher.repository.INotificationsRepository
import com.example.sportmatcher.service.INotificationService
import io.reactivex.Observable


class SendPushNotificationsUsecase(val notificationService: INotificationService, val notificationRepository: INotificationsRepository) :
    UseCase<NotificationType, Boolean> {
    override fun execute(payload: NotificationType): Boolean {
        val notifiableDevices : Observable<String> = notificationRepository.getNotifiableDevices()

        val subscribe = notifiableDevices.subscribe {
                it?.let{ notificationService.sendNotification(NotificationType.SIGNIN, it)}
        }

        //TODO kill the subscription of the notif
        return true
    }

}