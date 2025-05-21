package com.malharang.app.domain.usecase

import com.malharang.app.domain.model.DummyData
import com.malharang.app.domain.repository.DummyRepository
import javax.inject.Inject

class DummyUseCase @Inject constructor(
    private val dummyRepository: DummyRepository
) {
    suspend operator fun invoke(): Result<DummyData> = dummyRepository.dummy()
}