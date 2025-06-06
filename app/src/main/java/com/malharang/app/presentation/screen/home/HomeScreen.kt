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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.malharang.app.presentation.screen.home.component.PlaceTypeEmptyView
import com.malharang.app.presentation.screen.home.component.PlaceTypeListRow
import com.malharang.app.ui.theme.MalHaRangTheme
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import timber.log.Timber

@Composable
fun HomeRoute(
    padding: PaddingValues,
    navController: NavController,
    navigateToPlaceType: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // TODO: Dummy Data
    val userStatusModel = UserStatusModel(
        profileUrl = "https://avatars.githubusercontent.com/u/76648361?v=4&size=64",
        name = "Malssi",
        level = 5,
        exp = 70
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    LaunchedEffect(navBackStackEntry) {
        val type = navBackStackEntry?.savedStateHandle?.get<List<String>>("selected_place_types")
        if (type != null) {
            val currentTypes = viewModel.placeTypes.value
            val updatedTypes = (currentTypes + type).distinct()
            viewModel.setSelectedPlaceType(updatedTypes)

            navBackStackEntry.savedStateHandle.remove<List<String>>("selected_place_types")
        }
    }

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
    val placeTypes by viewModel.placeTypes.collectAsState()

    val navigateToPlaceTypeWithData = {
        navController.currentBackStackEntry?.savedStateHandle?.set("existing_types", placeTypes)
        navigateToPlaceType()
    }

    fun offsetLatLng(location: LatLng): LatLng {
        return LatLng(location.latitude - 0.003, location.longitude)
    }

    LaunchedEffect(Unit) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.fetchCurrentLocation()
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
        onRequestCurrentLocation = {
            viewModel.fetchCurrentLocation()
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(offsetLatLng(location), 16f))
        },
        navigateToPlaceType = navigateToPlaceTypeWithData,
        selectedPOI = selectedPOI,
        placeTypes = placeTypes,
        selectPOI = viewModel::selectPOI,
        cameraPositionState = cameraPositionState
    )
}

@Composable
private fun HomeScreen(
    padding: PaddingValues,
    userStatusModel: UserStatusModel,
    cameraPositionState: CameraPositionState,
    selectedPOI: PointOfInterest? = null,
    placeTypes: List<String>,
    navigateToPlaceType: () -> Unit = {},
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
                    myLocationButtonEnabled = true
                ),
                onPOIClick = { poi ->
                    selectPOI(poi)
                }
            ) {
                selectedPOI?.let {
                    Timber.tag("DEBUG_HOME").d("POI Id: %s", it.placeId)

                    Marker(
                        state = MarkerState(position = it.latLng),
                        title = it.name,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
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
                    text = "\uD83D\uDCCD 현재 위치: ${selectedPOI?.name ?: "한신대학교 경삼관"}",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                if (placeTypes.isNotEmpty()) {
                    PlaceTypeListRow(
                        placeTypes = placeTypes,
                        onAddClick = navigateToPlaceType
                    )
                    MissionCard() // TODO: MissionCardData 로 전달

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
                } else {
                    PlaceTypeEmptyView(onAddPlaceTypeClick = navigateToPlaceType)
                }
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
            cameraPositionState = rememberCameraPositionState(),
            placeTypes = listOf("park", "restaurant", "restaurant", "restaurant", "restaurant", "restaurant")
        )
    }
}
