package com.malharang.app.presentation.screen.home.component

import android.content.Context
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun CustomMarker(
    position: LatLng,
    context: Context,
    title: String? = null,
    snippet: String? = null,
    @DrawableRes resId: Int
) {
    val icon = remember {
        getBitmapDescriptorFromPng(
            context = context,
            resId = resId
        )
    }

    val markerState = remember(position) {
        MarkerState(position = position)
    }

    Marker(
        state = markerState,
        title = title,
        snippet = snippet,
        icon = icon,
        anchor = Offset(0.5f, 0.8f)
    )
}

fun getBitmapDescriptorFromPng(context: Context, resId: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, resId)
        ?: return BitmapDescriptorFactory.defaultMarker()

    val scale = context.resources.displayMetrics.density
    val desiredSize = (48 * scale).toInt()

    val bitmap = createBitmap(desiredSize, desiredSize)
    val canvas = Canvas(bitmap)

    drawable.setBounds(0, 0, desiredSize, desiredSize)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
