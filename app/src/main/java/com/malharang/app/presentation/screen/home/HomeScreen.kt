@file:OptIn(ExperimentalPermissionsApi::class)

package com.malharang.app.presentation.screen.home

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.malharang.app.R
import com.malharang.app.R.drawable.ic_home_my_location_24
import com.malharang.app.core.component.MissionCard
import com.malharang.app.core.component.UserStatusBar
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.presentation.model.UserStatusModel
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import timber.log.Timber

@Composable
fun HomeRoute(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // TODO: Dummy Data
    val userStatusModel = UserStatusModel(
        profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
        name = "Malssi",
        level = 5,
        exp = 70
    )

    // Maps
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val location by viewModel.location.collectAsState()

    var cameraOffsetLatLng = LatLng(location.latitude - 0.003, location.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraOffsetLatLng, 16f)
    }

    val selectedPOI by viewModel.selectedPOI.collectAsState()

    fun offsetLatLng(location: LatLng): LatLng {
        return LatLng(location.latitude - 0.003, location.longitude)
    }

    LaunchedEffect(Unit) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.fetchCurrentLocation()
            cameraOffsetLatLng = offsetLatLng(location)
        } else {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(location) {
        location.let {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(offsetLatLng(location), 16f)
            )
        }
    }

    HomeScreen(
        padding = padding,
        userStatusModel = userStatusModel,
        location = location,
        onRequestCurrentLocation = {
            viewModel.fetchCurrentLocation()
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(offsetLatLng(location), 16f))
        },
        selectedPOI = selectedPOI,
        selectPOI = viewModel::selectPOI,
        currentLocation = "한신대학교 경삼관",
        cameraPositionState = cameraPositionState
    )
}

@Composable
private fun HomeScreen(
    padding: PaddingValues,
    userStatusModel: UserStatusModel,
    location: LatLng = LatLng(37.5665, 126.9780),
    currentLocation: String,
    cameraPositionState: CameraPositionState,
    selectedPOI: PointOfInterest? = null,
    selectPOI: (PointOfInterest) -> Unit = {},
    onRequestCurrentLocation: () -> Unit = {},
    onLoadMoreContents: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        UserStatusBar(userStatusModel, modifier = Modifier.padding(top = 5.dp, bottom = 3.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.black),
            contentAlignment = Alignment.BottomCenter
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true,
                ),
                onPOIClick = { poi ->
                    Timber.tag("POI_TEST").d("클릭한 장소: %s", poi.name)
                    selectPOI(poi)
                }
            ) {
                Marker(
                    state = MarkerState(position = location),
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                    onClick = { true }
                )

                selectedPOI?.let {
                    Marker(
                        state = MarkerState(position = it.latLng),
                        title = it.name,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(colors.white60)
                    .clickable { onRequestCurrentLocation() }
                    .padding(6.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(ic_home_my_location_24),
                    contentDescription = "내 위치로 이동",
                    colorFilter = ColorFilter.tint(colors.grayDark)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, bottom = 30.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.white)
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    thickness = 4.dp,
                    modifier = Modifier
                        .width(36.dp)
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Text(
                    text = "\uD83D\uDCCD 현재 위치: $currentLocation",
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
                MissionCard() // TODO: MissionCardData 로 전달

                Spacer(modifier = Modifier.height(12.dp))

                MissionCard()

                Spacer(modifier = Modifier.height(12.dp))

                MissionCard()

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_home_chevron_down_24),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable { onLoadMoreContents() }
                        .padding(bottom = 15.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    MalHaRangTheme {
        HomeScreen(
            padding = PaddingValues(),
            userStatusModel = UserStatusModel(
                profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
                name = "Malssi",
                level = 5,
                exp = 70
            ),
            currentLocation = "한신대학교 경삼관",
            cameraPositionState = rememberCameraPositionState()
        )
    }
}
