package com.example.sportmatcher.domain.friendship

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.repository.IUserRepository
import io.reactivex.Single
import java.lang.IllegalStateException

class GetUserUseCase(private val iUserRepository: IUserRepository): UseCase<String?, Single<User>>{

    override fun execute(payload: String?): Single<User> {
        if(payload.isNullOrBlank())
            return Single.error(IllegalStateException("invalid uid"))
        return iUserRepository.getUser(payload)
    }
}