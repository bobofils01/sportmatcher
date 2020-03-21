package com.example.sportmatcher.domain.preferences

import com.example.sportmatcher.di.ServiceProvider
import com.example.sportmatcher.di.ServiceProvider.getAuthenticatedState
import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.AuthenticationState
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.model.authentication.NotAuthenticated
import com.example.sportmatcher.repository.IUserRepository
import com.example.sportmatcher.service.IAuthService
import com.example.sportmatcher.service.INotificationService
import io.reactivex.Single
import java.lang.IllegalStateException

class UpdateSportsFavouriteUsecase(private val iUserRepository: IUserRepository, private val iNotificationService: INotificationService) :
    UseCase<ArrayList<String>, Boolean> {
    override fun execute(listAfter: ArrayList<String>): Boolean {
        getAuthenticatedState.execute().subscribe{ authState ->
            when(authState){
                is AuthenticatedState -> {
                     val authUser = authState.user
                    iUserRepository.getUserSportFavourite(authUser.uid!!).subscribe{
                        listBefore ->
                            //Sync the subscription to topics for notificaition
                            val topicsToUnsubscribeFrom = ArrayList(listBefore.filter { it ->  !listAfter.contains(it)})
                            iNotificationService.unsubscribeFromSportTopic(topicsToUnsubscribeFrom);
                            val toicsToSubscribeTo = ArrayList(listAfter.filter{ it -> !listBefore.contains(it)})
                            iNotificationService.subscribeToSportTopic(toicsToSubscribeTo)

                            //update the list in userPref
                            iUserRepository.updateUserSportFavourite(authUser.uid!!, listAfter)

                    }
                }
                is NotAuthenticated ->{
                    throw IllegalStateException("can't sync preferences without being authenticated.")
                }
            }
        }



        return true
    }

}