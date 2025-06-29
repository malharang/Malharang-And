package com.malharang.app.data.mapper.todata

import com.malharang.app.data.remote.dto.common.ChatMessageDto
import com.malharang.app.data.remote.dto.common.ChatStateDto
import com.malharang.app.domain.model.ChatMessageData
import com.malharang.app.domain.model.ChatStateData

fun ChatStateData.toData(): ChatStateDto = ChatStateDto(
    mode = this.mode,
    selectedLocation = this.selectedLocation,
    selectedScenario = this.selectedScenario,
    messages = this.messages.map { it.toData() }
)

fun ChatMessageData.toData(): ChatMessageDto = ChatMessageDto(
    role = this.role,
    content = this.content
)