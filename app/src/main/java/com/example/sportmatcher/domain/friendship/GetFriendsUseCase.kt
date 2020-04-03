package com.example.sportmatcher.domain.friendship

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.repository.IUserRepository
import io.reactivex.Observable
import java.lang.IllegalStateException

class GetFriendsUseCase(private val iUserRepository: IUserRepository): UseCase<String, Observable<List<User>>> {

    override fun execute(payload: String): Observable<List<User>> {
        if(payload.isNullOrBlank())
            return Observable.error(IllegalStateException("Invalid user id"))
        return iUserRepository.getAllFriends(payload)
    }

}
