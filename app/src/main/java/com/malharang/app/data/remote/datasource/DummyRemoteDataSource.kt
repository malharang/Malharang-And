package com.malharang.app.data.remote.datasource

import com.malharang.app.data.remote.dto.response.DummyResponseDto

interface DummyRemoteDataSource {
    suspend fun dummy(): DummyResponseDto
}