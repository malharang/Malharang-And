package com.malharang.app.data.remote.datasourceimpl

import com.malharang.app.data.mapper.todata.toData
import com.malharang.app.data.remote.datasource.ChatRemoteDataSource
import com.malharang.app.data.remote.dto.request.ChatRequestDto
import com.malharang.app.data.remote.dto.response.ChatResponseDto
import com.malharang.app.data.remote.service.ChatService
import com.malharang.app.domain.model.ChatStateData
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(
    private val chatService: ChatService
) : ChatRemoteDataSource {
    override suspend fun postChat(
        userInput: String,
        state: ChatStateData
    ): ChatResponseDto =
        chatService.postChat(
            request = ChatRequestDto(
                userInput = userInput,
                state = state.toData()
            )
        )
}
