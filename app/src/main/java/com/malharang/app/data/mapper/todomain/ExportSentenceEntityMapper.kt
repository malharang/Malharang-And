package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.local.entity.ExportSentenceEntity
import com.malharang.app.domain.model.ExportSentenceData

fun ExportSentenceEntity.toDomain(): ExportSentenceData =
    ExportSentenceData(
        id = id,
        sentence = sentence,
        translation = translation,
        savedAt = savedAt
    )
