package com.example.sportmatcher.repository

import io.reactivex.Observable

interface INotificationsRepository {

    fun getNotifiableDevices(): Observable<String>
    fun addNotifiableDevice(token: String?)
}