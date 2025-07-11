package com.malharang.app.domain.model

data class ExportSentenceData(
    val id: Long = 0,
    val sentence: String,
    val translation: String,
    val savedAt: Long
)
