package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.remote.datasource.ChatRemoteDataSource
import com.malharang.app.domain.model.ChatData
import com.malharang.app.domain.model.ChatStateData
import com.malharang.app.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {
    override suspend fun postChat(
        userInput: String,
        state: ChatStateData
    ): Result<ChatData> = runCatching {
        chatRemoteDataSource.postChat(
            userInput = userInput,
            state = state
        ).toDomain()
    }
}
