package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.local.entity.ConversationEntity
import com.malharang.app.domain.model.ConversationData

fun ConversationEntity.toDomain(): ConversationData {
    return ConversationData(
        id = this.id,
        selectedLocation = this.selectedLocation,
        selectedScenario = this.selectedScenario,
        mode = this.mode
    )
}
