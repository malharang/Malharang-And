package com.malharang.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "message",
    foreignKeys = [
        ForeignKey(
            entity = ConversationEntity::class,
            parentColumns = ["id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "conversation_id", index = true)
    val conversationId: Long,

    val role: String,
    val content: String,

    // Evaluation fields (only for user messages)
    val contextualityPassed: Boolean? = null,
    val contextualityComment: String? = null,
    val grammarPassed: Boolean? = null,
    val grammarComment: String? = null,
    val grammarErrors: String? = null // JSON string of grammar errors
)
