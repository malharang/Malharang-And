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

    val passed: Boolean? = null,
    val commentContextuality: String? = null,
    val commentLexicalVariety: String? = null
)
