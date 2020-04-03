package com.example.sportmatcher.domain.friendship

import com.example.sportmatcher.domain.NoInputUseCase
import com.example.sportmatcher.model.User
import com.example.sportmatcher.repository.IUserRepository
import io.reactivex.Observable

class GetAllUsersUseCase(private val iUserRepository: IUserRepository) :NoInputUseCase<Observable<List<User>>>{

    override fun execute(): Observable<List<User>> {
        return iUserRepository.getAllUser()
    }

}
