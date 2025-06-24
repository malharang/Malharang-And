package com.malharang.app.data.remote.service

import com.malharang.app.BuildConfig
import com.malharang.app.data.remote.dto.response.TranslateResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateService {
    @GET("/language/translate/v2")
    suspend fun getTranslate(
        @Query("q") text: String,
        @Query("target") language: String,
        @Query("source") source: String = "ko",
        @Query("key") key: String = BuildConfig.TRANSLATE_API_KEY
    ): TranslateResponseDTO
}
