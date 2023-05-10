package com.dani.final2.screens

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.dani.final2.R
import com.dani.final2.appData.*
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import java.lang.reflect.InvocationTargetException

@Composable
fun MapasScreen(navController: NavHostController) {
    Mapas(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mapas(navController: NavHostController) {
    var ng = NoteGetter().getNotes()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val singapore = LatLng(1.35, 103.87)
    var ubicaActual = LatLng(lcd.get(1), lcd.get(2))
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ubicaActual, 16.5f)
    }
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)

    ) {

        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(bottom = 80.dp)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.shapes.medium
                )
                .align(Alignment.Center)
                .zIndex(5f)


        ) {
        }


        Scaffold(
            modifier = Modifier.zIndex(1f),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(ubicaActual, 16.5f)
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(60.dp)
                ) {
                    Icon(
                        Icons.Filled.GpsFixed,
                        contentDescription = "Camera",
                        tint = MaterialTheme.colorScheme.inverseSurface
                    )
                }

            },
            bottomBar = {
                Column(
                ) {
                    BottomAppBar(
                        containerColor = Color.Transparent,
                        modifier = Modifier
                            .height(90.dp)
                            .padding(7.dp)
                            .background(Color.Transparent, MaterialTheme.shapes.medium)
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                    var selectedItem by remember { mutableStateOf(2) }
                    val items = listOf("Inicio", "Notas", "Mapa", "Cámara", "Ajustes")
                    val itemsIcon =
                        listOf(
                            Icons.Filled.Home,
                            Icons.Filled.Notes,
                            Icons.Filled.Map,
                            Icons.Filled.Camera,
                            Icons.Filled.Settings
                        )
                        listOf(Icons.Filled.Notes, Icons.Filled.Map, Icons.Filled.Camera)
                    NavigationBar(modifier = Modifier.height(83.dp)) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(itemsIcon[index], contentDescription = item) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    selectedItem = index
                                    when (index) {
                                        0 -> {
                                            navController.navigate("homeScreen")
                                        }
                                        1 -> {
                                            navController.navigate("listas")
                                        }
                                        2 -> {
                                            navController.navigate("mapasScreen")
                                        }
                                        3 -> {
                                            selectedItem = 3
                                        }
                                        4 -> {
                                            selectedItem = 4
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            },
        ) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)


            Text(
                text = "Mapas",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(it)
            )


            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 0.dp),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    zoomGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    tiltGesturesEnabled = true,
                    compassEnabled = false,
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = false,
                ), properties = MapProperties(
                    mapType = MapType.NONE,


                ), googleMapOptionsFactory = {
                    GoogleMapOptions()
                        .backgroundColor(Color.Transparent.hashCode())

                }


            ) {

                for (i in 0..noteList.size - 1) {

                    com.google.maps.android.compose.GroundOverlay(
                        position = GroundOverlayPosition.create(
                            LatLng(noteList.get(i).x.toDouble(), noteList.get(i).y.toDouble()),
                            200f,
                            200f
                        ),
                        image = BitmapDescriptorFactory.fromResource(R.drawable.inverse_control),
                    )
                    Marker(
                        state = rememberMarkerState(
                            "nota",
                            position = LatLng(
                                noteList.get(i).x.toDouble(),
                                noteList.get(i).y.toDouble()
                            )
                        ),
                        draggable = false,
                        title = noteList.get(i).text,
                        flat = false,
                        zIndex = i.toFloat()
                        //icon = BitmapDescriptorFactory.fromResource(R.drawable.mapafondo),


                    )
                    Polyline(
                        points = listOf(
                            ubicaActual,
                            LatLng(noteList.get(i).x.toDouble(), noteList.get(i).y.toDouble())
                        ), color = Color.White, width = 5f, zIndex = 1f
                    )
                }
                /*
                Circle(
                    center = ubicaActual,
                    radius = 180.0,
                    fillColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                    strokeColor = MaterialTheme.colorScheme.primary,
                    zIndex = 1f,
                    strokeWidth = 11f,
                    tag = "circle",

                    )
                Circle(
                    center = ubicaActual,
                    radius = 290.0,
                    fillColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                    strokeColor = MaterialTheme.colorScheme.primary,
                    zIndex = 1f,
                    strokeWidth = 11f,
                    tag = "circle",

                    )
*/

            }


        }
    }
}