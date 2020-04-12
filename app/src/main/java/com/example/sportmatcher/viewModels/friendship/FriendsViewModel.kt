package com.example.sportmatcher.viewModels.friendship

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.dto.friendship.InvitationDTO
import com.example.sportmatcher.model.User
import com.example.sportmatcher.viewModels.AbstractViewModel

class FriendsViewModel: AbstractViewModel() {

    private val actualUser by lazy {
        MutableLiveData<User>()
    }

    private val friendsMutableData by lazy { MutableLiveData<ArrayList<User>>() }

    private val addFriendUseCase by lazy{
        ServiceProvider.addFriendUseCase
    }

    private val deleteFriendUseCase by lazy{
        ServiceProvider.deleteFriendUseCase
    }

    private val getFriendsUseCase by lazy{
        ServiceProvider.getFriendsUseCase
    }

    private val getAllUsersUseCase by lazy{
        ServiceProvider.getAllUsersUseCase
    }

    private val getUserUseCase by lazy{
        ServiceProvider.getUserUseCase
    }

    fun getAllUsers(): MutableLiveData<ArrayList<User>> {
        val usersMutableData = MutableLiveData<ArrayList<User>>()
        compositeDisposable.add(
            getAllUsersUseCase.execute().subscribe{
                usersMutableData.value = it.filter { user ->
                    user.uid!=actualUser.value!!.uid && !friendsMutableData.value!!.map { u-> u.uid }.contains(user.uid)
                } as ArrayList<User>
            }
        )
        return usersMutableData
    }

    fun getAllFriends(): MutableLiveData<ArrayList<User>> {
        compositeDisposable.add(
            getFriendsUseCase.execute(actualUser.value!!.uid!!).subscribe{
                friendsMutableData.value = it as ArrayList<User>
            }
        )
        return friendsMutableData
    }

    fun addFriend(user: User){
        addFriendUseCase.execute(InvitationDTO(from=actualUser.value!!, to=user))
        Log.d("ADDED", actualUser.value!!.email +" "+ user.email)
    }

    fun deleteFriend(user: User){
        deleteFriendUseCase.execute(InvitationDTO(from=actualUser.value!!, to=user))
        Log.d("DELETED", actualUser.value!!.email +" "+ user.email)
    }

    fun setUser(user: User){
            actualUser.value = user
    }
}