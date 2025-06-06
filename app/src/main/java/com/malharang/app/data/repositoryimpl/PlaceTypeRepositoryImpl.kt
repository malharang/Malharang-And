package com.malharang.app.data.repositoryimpl

import android.content.Context
import com.malharang.app.R
import com.malharang.app.domain.repository.PlaceTypeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import javax.inject.Inject

class PlaceTypeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PlaceTypeRepository {
    private val recentKey = "recent_place_types"
    private val prefs by lazy { context.getSharedPreferences("place_type", Context.MODE_PRIVATE) }
    private val allTypes by lazy { loadTypesFromRaw() }

    private fun loadTypesFromRaw(): List<String> {
        val inputStream = context.resources.openRawResource(R.raw.place_types)
        val json = inputStream.bufferedReader().use { it.readText() }
        val array = JSONArray(json)
        return List(array.length()) { array.getString(it) }
    }

    override suspend fun getRecentPlaceTypes(): List<String> =
        prefs.getStringSet(recentKey, emptySet())?.toList() ?: emptyList()
}
