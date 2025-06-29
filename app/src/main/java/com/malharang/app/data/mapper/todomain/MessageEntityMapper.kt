package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.local.entity.MessageEntity
import com.malharang.app.domain.model.MessageData

fun MessageEntity.toDomain(): MessageData = MessageData(
    id = this.id,
    conversationId = this.conversationId,
    role = this.role,
    content = this.content,
    passed = this.passed,
    commentContextuality = this.commentContextuality,
    commentLexicalVariety = this.commentLexicalVariety
)