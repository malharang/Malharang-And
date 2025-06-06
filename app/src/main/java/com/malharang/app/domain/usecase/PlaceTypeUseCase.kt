package com.malharang.app.domain.usecase

import com.malharang.app.domain.repository.PlaceTypeRepository
import javax.inject.Inject

class PlaceTypeUseCase @Inject constructor(
    private val repository: PlaceTypeRepository
) {
    suspend fun getRecentPlaceTypes() = repository.getRecentPlaceTypes()
}
