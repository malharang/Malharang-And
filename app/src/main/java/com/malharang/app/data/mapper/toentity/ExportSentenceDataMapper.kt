package com.malharang.app.data.mapper.toentity

import com.malharang.app.data.local.entity.ExportSentenceEntity
import com.malharang.app.domain.model.ExportSentenceData

fun ExportSentenceData.toEntity(): ExportSentenceEntity =
    ExportSentenceEntity(
        id = id,
        sentence = sentence,
        translation = translation,
        savedAt = savedAt
    )
