package com.malharang.app.data.mapper.todata

import com.malharang.app.data.remote.dto.request.ChatMessageDto
import com.malharang.app.domain.model.MessageData

fun MessageData.toChatMessageDto(): ChatMessageDto {
    return ChatMessageDto(
        role = this.role,
        content = this.content
    )
}
