package com.malharang.app.data.mapper.todomain

import com.malharang.app.data.remote.dto.response.DummyResponseDto
import com.malharang.app.domain.model.DummyData

fun DummyResponseDto.toDomain(): DummyData {
    return DummyData(
        description = this.dummy + "입니다 "
    )
}