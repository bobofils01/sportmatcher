package com.example.sportmatcher.repository

import com.example.sportmatcher.model.User
import io.reactivex.Single

interface IUserRepository {
    //Single
    //Observable
    //Completable

    fun createUser(user: User): Single<User>
    fun updateUser(user: User): Single<User>
    fun getUser(uid: String): Single<User>

}