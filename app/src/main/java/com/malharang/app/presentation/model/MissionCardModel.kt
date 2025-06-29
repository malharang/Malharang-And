package com.malharang.app.presentation.model

data class MissionCardModel(
    val title: String,
    val type: PlaceTypeItem = PlaceTypeItem.Location("placeType")
)
