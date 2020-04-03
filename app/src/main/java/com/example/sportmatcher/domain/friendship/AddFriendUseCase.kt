package com.example.sportmatcher.domain.friendship

import com.example.sportmatcher.domain.UseCase
import com.example.sportmatcher.dto.friendship.InvitationDTO
import com.example.sportmatcher.repository.IUserRepository

class AddFriendUseCase(private val iUserRepository: IUserRepository): UseCase<InvitationDTO, Boolean>{

    override fun execute(payload: InvitationDTO): Boolean {
        iUserRepository.sendAndReceiveInvitation(payload)
        return true
    }
}