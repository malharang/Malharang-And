package com.malharang.app.domain.mapper

import com.malharang.app.domain.model.ChatMessageData
import com.malharang.app.domain.model.MessageData
import com.malharang.app.presentation.model.ChatMessageModel
import com.malharang.app.presentation.model.SenderType

fun List<MessageData>.toChatMessageModelList(): List<ChatMessageModel> {
    return this.mapNotNull { msg ->
        val sender = when (msg.role) {
            "user" -> SenderType.USER
            "assistant" -> SenderType.BOT
            else -> null
        }

        sender?.let {
            ChatMessageModel(
                text = msg.content,
                sender = it
            )
        }
    }
}

fun List<MessageData>.toChatMessageDataList(): List<ChatMessageData> {
    return this.map {
        ChatMessageData(
            role = it.role,
            content = it.content
        )
    }
}
