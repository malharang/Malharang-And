package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.TranslationDataDTO
import com.malharang.app.domain.model.TranslateData

fun TranslationDataDTO.toDomain(): TranslateData = TranslateData(
    translatedText = this.translations[0].translatedText
)
