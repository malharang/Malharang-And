package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.ChatData
import com.malharang.app.domain.model.ChatStateData
import com.malharang.app.domain.repository.ChatRepository
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        userInput: String,
        state: ChatStateData
    ): Result<ChatData> = chatRepository.postChat(
        userInput = userInput,
        state = state
    )
}
