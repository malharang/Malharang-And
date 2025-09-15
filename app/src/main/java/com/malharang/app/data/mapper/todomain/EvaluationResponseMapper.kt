package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.ContextualityDto
import com.malharang.app.data.remote.dto.response.EvaluationDataDto
import com.malharang.app.data.remote.dto.response.EvaluationResponseDto
import com.malharang.app.data.remote.dto.response.GrammarDto
import com.malharang.app.data.remote.dto.response.GrammarItemDto
import com.malharang.app.domain.model.ContextualityData
import com.malharang.app.domain.model.EvaluationResponseData
import com.malharang.app.domain.model.EvaluationResultData
import com.malharang.app.domain.model.GrammarData
import com.malharang.app.domain.model.GrammarErrorData

fun EvaluationResponseDto.toDomain(): EvaluationResponseData {
    return EvaluationResponseData(
        data = this.data.toDomain(),
        message = this.message,
        success = this.success
    )
}

fun EvaluationDataDto.toDomain(): EvaluationResultData {
    return EvaluationResultData(
        contextuality = this.contextuality.toDomain(),
        grammar = this.grammar.toDomain()
    )
}

fun ContextualityDto.toDomain(): ContextualityData {
    return ContextualityData(
        comment = this.comment,
        contextuality = emptyList(), // ContextualityItemDto는 사용하지 않으므로 빈 리스트
        pass = this.pass
    )
}

fun GrammarDto.toDomain(): GrammarData {
    return GrammarData(
        comment = this.comment,
        grammar = this.grammar.map { it.toDomain() },
        pass = this.pass
    )
}

fun GrammarItemDto.toDomain(): GrammarErrorData {
    return GrammarErrorData(
        originalSentence = this.originalSentence,
        correctedSentence = this.correctedSentence,
        errorExplanation = this.errorExplanation,
        simpleExplanation = this.simpleExplanation
    )
}
