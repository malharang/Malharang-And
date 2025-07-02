package com.malharang.app.data.mapper.toentity

import com.malharang.app.data.local.entity.ConversationEntity
import com.malharang.app.domain.model.ConversationData

fun ConversationData.toEntity(): ConversationEntity {
    return ConversationEntity(
        id = this.id,
        selectedLocation = this.selectedLocation,
        selectedScenario = this.selectedScenario,
        mode = this.mode
    )
}
