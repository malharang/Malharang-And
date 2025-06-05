package com.malharang.app.presentation.model

sealed interface LocationPermissionState {
    object Granted : LocationPermissionState
    object Denied : LocationPermissionState
}
