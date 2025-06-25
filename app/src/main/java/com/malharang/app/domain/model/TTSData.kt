package com.malharang.app.domain.model

import okhttp3.ResponseBody

data class TTSData (
    val audioStream: ResponseBody
)