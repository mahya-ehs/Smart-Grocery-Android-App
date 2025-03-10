package com.example.finalproject.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.ui.components.ShoppingContainer
import com.example.finalproject.viewmodel.ShoppingHistoryViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

@Composable
fun MapScreen(pagerState: PagerState,
              viewModel: ShoppingHistoryViewModel = viewModel()
) {

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState()
    val supermarkets = remember { mutableStateListOf<Pair<String, LatLng>>() }
    val permissionState = remember { mutableStateOf(false) }
    val userLocation = remember { mutableStateOf<LatLng?>(null) }
    val selectedSupermarket = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    var shouldGoNext by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> permissionState.value = isGranted }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            permissionState.value = true
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(permissionState.value) {
        if (permissionState.value) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    userLocation.value = userLatLng
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(userLatLng, 14f))
                }
            }
        }
    }

    LaunchedEffect(userLocation.value) {
        userLocation.value?.let { location ->
            val fetchedSupermarkets = fetchSupermarkets(location.latitude, location.longitude)
            supermarkets.clear()
            supermarkets.addAll(fetchedSupermarkets)
        }
    }

    LaunchedEffect(shouldGoNext) {
        if (shouldGoNext) {
            pagerState.animateScrollToPage(3)
            shouldGoNext = false
        }
    }
    ShoppingContainer(
        backgroundImage = R.drawable.blob,
        content = {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = permissionState.value)
            ) {
                supermarkets.forEach { (name, location) ->
                    Marker(
                        state = rememberMarkerState(position = location),
                        title = name,
                        onClick = {
                            selectedSupermarket.value = name
                            false
                        }
                    )
                }
            }
        },
        bottomBar = {}
    )


    selectedSupermarket.value?.let { name ->
        AlertDialog(
            title = { Text("Confirm Store") },
            text = { Text("Are you sure you want to select $name?") },
            onDismissRequest = { selectedSupermarket.value = null },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setSelectedStore(name)
                    shouldGoNext = true
                    selectedSupermarket.value = null
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedSupermarket.value = null }) {
                    Text("Dismiss")
                }
            }
        )
    }



}


suspend fun fetchSupermarkets(lat: Double, lon: Double) : List<Pair<String, LatLng>> {
    return withContext(Dispatchers.IO) {
        try {
            val query = """
                [out:json];
                (
                  node["shop"~"supermarket|grocery|department_store|discount|variety_store|general"](around:5000, $lat, $lon);
                  way["shop"~"supermarket|grocery|department_store|discount|variety_store|general"](around:5000, $lat, $lon);
                  relation["shop"~"supermarket|grocery|department_store|discount|variety_store|general"](around:5000, $lat, $lon);
                );
                out center;
            """.trimIndent()

            val url = "https://overpass-api.de/api/interpreter?data=${query.replace("\n", "")}"
            val response = URL(url).readText()
            val jsonObject = JSONObject(response)   // converting json raw data into obj
            val elements = jsonObject.getJSONArray("elements")
            val supermarkets = mutableListOf<Pair<String, LatLng>>()

            for (i in 0 until elements.length()) {
                val element = elements.getJSONObject(i)
                var lat = element.optDouble("lat", Double.NaN)
                var lon = element.optDouble("lon", Double.NaN)
                val name = element.optJSONObject("tags")?.optString("name", "Unnamed Supermarket")
                    ?: "Unnamed Supermarket"

                // if the type is 'way' or 'relation', we should use lat and lon of center
                if (lat.isNaN() || lon.isNaN()) {
                    val center = element.optJSONObject("center")
                    if (center != null) {
                        lat = center.getDouble("lat")
                        lon = center.getDouble("lon")
                    }
                }
                supermarkets.add(name to LatLng(lat, lon))
            }
            supermarkets

        } catch (e : Exception) {
            emptyList()

        }
    }

}
