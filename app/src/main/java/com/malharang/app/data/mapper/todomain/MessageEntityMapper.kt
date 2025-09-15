package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.local.entity.MessageEntity
import com.malharang.app.domain.model.MessageData

fun MessageEntity.toDomain(): MessageData = MessageData(
    id = this.id,
    conversationId = this.conversationId,
    role = this.role,
    content = this.content,
    contextualityPassed = this.contextualityPassed,
    contextualityComment = this.contextualityComment,
    grammarPassed = this.grammarPassed,
    grammarComment = this.grammarComment,
    grammarErrors = this.grammarErrors
)
