package com.malharang.app.data.remote.service

import com.malharang.app.data.remote.dto.response.DummyResponseDto
import retrofit2.http.GET

interface DummyService {
    @GET("/API")
    suspend fun dummy(): DummyResponseDto
}