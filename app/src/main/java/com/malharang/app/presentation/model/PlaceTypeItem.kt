package com.malharang.app.presentation.model

sealed class PlaceTypeItem {
    data class Location(val name: String) : PlaceTypeItem()
    data class Goal(val name: String) : PlaceTypeItem()
}
