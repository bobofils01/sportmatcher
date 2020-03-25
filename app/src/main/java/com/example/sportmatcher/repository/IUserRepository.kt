package com.example.sportmatcher.repository

import com.example.sportmatcher.model.User
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

interface IUserRepository {
    //Single
    //Observable
    //Completable

    fun createUser(user: User): Single<User>
    fun updateUser(user: User): Single<User>
    fun getUser(uid: String): Single<User>
    fun getUserSportFavourite(uid: String): Single<ArrayList<String>>
    fun updateUserSportFavourite(uuid: String, list : ArrayList<String>)

}