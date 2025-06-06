package com.malharang.app.data.repositoryimpl

import android.content.Context
import com.malharang.app.domain.repository.PlaceTypeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlaceTypeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PlaceTypeRepository {
    private val recentKey = "recent_place_types"
    private val prefs by lazy { context.getSharedPreferences("place_type", Context.MODE_PRIVATE) }

    override suspend fun getRecentPlaceTypes(): List<String> =
        prefs.getStringSet(recentKey, emptySet())?.toList() ?: emptyList()
}
