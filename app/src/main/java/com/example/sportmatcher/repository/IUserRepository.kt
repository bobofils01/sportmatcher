package com.example.sportmatcher.repository

import com.example.sportmatcher.dto.friendship.InvitationDTO
import com.example.sportmatcher.model.User
import io.reactivex.Single
import kotlin.collections.ArrayList
import io.reactivex.Observable

interface IUserRepository {
    //Single
    //Observable
    //Completable

    fun createUser(user: User): Single<User>
    fun updateUser(user: User): Single<User>
    fun getUser(uid: String): Single<User>
    fun getUserSportFavourite(uid: String): Single<ArrayList<String>>
    fun updateUserSportFavourite(uuid: String, list : ArrayList<String>)
    fun getAllUser(): Observable<List<User>>
    fun sendAndReceiveInvitation(invitation: InvitationDTO)
    fun getAllFriends(uid: String): Observable<List<User>>
    fun deleteFriend(payload: InvitationDTO)
    //fun receiveInvitation(invitation: Invitation)
    //fun sendAndReceiveInvitation(invitation: Invitation): Single<User>
}
