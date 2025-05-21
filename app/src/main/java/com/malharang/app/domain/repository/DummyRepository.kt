package com.malharang.app.domain.repository

import com.malharang.app.domain.model.DummyData

interface DummyRepository {
    suspend fun dummy(): Result<DummyData>
}