package com.example.sportmatcher.viewModels.friendship

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.dto.friendship.InvitationDTO
import com.example.sportmatcher.model.User
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class FriendsViewModel: ViewModel() {

    private val actualUser by lazy {
        MutableLiveData<User>()
    }

    private val friendsMutableData by lazy { MutableLiveData<ArrayList<User>>() }

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

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
        getAllUsersUseCase.execute().subscribe{
            usersMutableData.value = it.filter { user -> !friendsMutableData.value!!.map { u-> u.uid }.contains(user.uid) } as ArrayList<User>
        }
        return usersMutableData
    }

    fun getAllFriends(uid: String): MutableLiveData<ArrayList<User>> {
        getFriendsUseCase.execute(uid).subscribe{
            friendsMutableData.value = it as ArrayList<User>
        }
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

    fun setUser(uid: String?){
        getUserUseCase.execute(uid).subscribe{ user->
            Log.d("ViewModelFriends", user.friends.toString())
            actualUser.value = user
        }
    }
}