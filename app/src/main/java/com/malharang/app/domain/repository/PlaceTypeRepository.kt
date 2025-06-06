package com.malharang.app.domain.repository

interface PlaceTypeRepository {
    suspend fun getRecentPlaceTypes(): List<String>
}
