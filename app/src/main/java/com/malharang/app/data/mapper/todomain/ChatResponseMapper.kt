package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.common.ChatMessageDto
import com.malharang.app.data.remote.dto.common.ChatStateDto
import com.malharang.app.data.remote.dto.response.ChatResponseDto
import com.malharang.app.domain.model.ChatData
import com.malharang.app.domain.model.ChatMessageData
import com.malharang.app.domain.model.ChatStateData

fun ChatResponseDto.toDomain(): ChatData = ChatData(
    reply = this.data.reply,
    state = this.data.state.toDomain()
)

fun ChatStateDto.toDomain(): ChatStateData = ChatStateData(
    mode = this.mode,
    selectedLocation = this.selectedLocation,
    selectedScenario = this.selectedScenario,
    messages = this.messages.map { it.toDomain() }
)

fun ChatMessageDto.toDomain(): ChatMessageData = ChatMessageData(
    role = this.role,
    content = this.content
)
