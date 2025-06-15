package com.malharang.app.presentation.model

import com.google.android.gms.maps.model.LatLng

data class PlaceInfoModel(
    val name: String,
    val latLng: LatLng,
    val types: List<String>
)
