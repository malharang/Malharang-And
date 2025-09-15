package com.malharang.app.data.mapper.todto

import com.malharang.app.data.remote.dto.request.EvaluationRequestDto
import com.malharang.app.data.remote.dto.request.MessageDto
import com.malharang.app.domain.model.EvaluationRequestData
import com.malharang.app.domain.model.MessageData

fun EvaluationRequestData.toDto(): EvaluationRequestDto {
    return EvaluationRequestDto(
        messages = this.messages.map { it.toDto() }
    )
}

fun MessageData.toDto(): MessageDto {
    return MessageDto(
        role = this.role,
        content = this.content
    )
}