package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todomain.toDomain
import com.malharang.app.data.remote.datasource.DummyRemoteDataSource
import com.malharang.app.domain.model.DummyData
import com.malharang.app.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyRemoteDataSource: DummyRemoteDataSource
) : DummyRepository {
    override suspend fun dummy(): Result<DummyData> = runCatching {
        dummyRemoteDataSource.dummy().toDomain()
    }
}