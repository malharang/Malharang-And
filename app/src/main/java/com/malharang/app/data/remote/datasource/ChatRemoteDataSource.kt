package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.response.ChatResponseDto
import com.malharang.app.domain.model.ChatStateData

interface ChatRemoteDataSource {
    suspend fun postChat(
        userInput: String,
        state: ChatStateData
    ): ChatResponseDto
}
