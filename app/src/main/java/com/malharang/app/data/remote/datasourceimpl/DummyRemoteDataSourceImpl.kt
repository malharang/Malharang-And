package com.malharang.app.data.remote.datasourceimpl

import com.malharang.app.data.remote.datasource.DummyRemoteDataSource
import com.malharang.app.data.remote.dto.response.DummyResponseDto
import com.malharang.app.data.remote.service.DummyService
import javax.inject.Inject

class DummyRemoteDataSourceImpl @Inject constructor(
    private val service: DummyService
) : DummyRemoteDataSource {
    override suspend fun dummy(): DummyResponseDto =
        service.dummy()
}