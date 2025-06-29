package com.malharang.app.data.remote.service

import com.malharang.app.data.remote.dto.request.ChatRequestDto
import com.malharang.app.data.remote.dto.response.ChatResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatService {
    @POST("/dialogue/chat")
    suspend fun postChat(
        @Body request: ChatRequestDto
    ): ChatResponseDto
}