// ConversationDataStore.kt
package com.malharang.app.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "conversation_pref"

val Context.conversationDataStore by preferencesDataStore(name = DATASTORE_NAME)

private val RECENT_CONVERSATION_ID = longPreferencesKey("recent_conversation_id")

class ConversationDataStore(private val context: Context) {

    suspend fun getRecentConversationId(): Long? =
        context.conversationDataStore.data.map { prefs ->
            prefs[RECENT_CONVERSATION_ID]
        }.firstOrNull()

    suspend fun saveConversationId(id: Long) {
        context.conversationDataStore.edit { prefs ->
            prefs[RECENT_CONVERSATION_ID] = id
        }
    }

    suspend fun clearConversationId() {
        context.conversationDataStore.edit { prefs ->
            prefs.remove(RECENT_CONVERSATION_ID)
        }
    }
}
