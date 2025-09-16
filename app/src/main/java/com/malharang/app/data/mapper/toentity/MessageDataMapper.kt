package com.malharang.app.data.mapper.toentity

import com.malharang.app.data.local.entity.MessageEntity
import com.malharang.app.domain.model.MessageData

fun MessageData.toEntity(): MessageEntity = MessageEntity(
    id = this.id,
    conversationId = this.conversationId,
    role = this.role,
    content = this.content,
    contextualityPassed = this.contextualityPassed,
    contextualityComment = this.contextualityComment,
    contextualityErrors = this.contextualityErrors,
    grammarPassed = this.grammarPassed,
    grammarComment = this.grammarComment,
    grammarErrors = this.grammarErrors
)
