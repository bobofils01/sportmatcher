package com.example.sportmatcher.domain.notifications

import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.repository.INotificationsRepository
import com.example.sportmatcher.service.INotificationService

class RegisterToNotificationsUseCase(val notificationRepo : INotificationsRepository, val notificationService : INotificationService) : NoInputUseCase<Boolean> {
    override fun execute(): Boolean {
       val deviceToken = notificationService.getCurrentNotificationToken()

       val subscribe = deviceToken.subscribe{
           token ->
           notificationRepo.addNotifiableDevice(token)
       }
        //TODO kill the subscription
        return true
    }
}