package com.malharang.app.domain.repository

import com.malharang.app.domain.model.ChatData
import com.malharang.app.domain.model.ChatStateData

interface ChatRepository {
    suspend fun postChat(
        userInput: String,
        state: ChatStateData
    ): Result<ChatData>
}
