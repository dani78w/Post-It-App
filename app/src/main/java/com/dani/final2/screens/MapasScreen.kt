package com.dani.final2.screens

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
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



    NoteGetter().getNotes()

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val singapore = LatLng(1.35, 103.87)

    var ubicaActual = remember {
        mutableStateOf(singapore)
    }

    if(lcd.size>0){
        ubicaActual.value = LatLng(lcd.get(1), lcd.get(2))
    }
    val ubicaInicio = ubicaActual
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ubicaActual.value, 16.5f)
    }

    var noteindex= remember {
        mutableStateOf(0)
    }
    var noteDistance= remember {
        mutableStateOf(0.056)
    }


    fun nextMarker(){
        if (noteindex.value < noteList.size - 1) {
            noteindex.value++
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(
                    noteList.get(noteindex.value).x.toDouble(),
                    noteList.get(noteindex.value).y.toDouble()
                ), 16.5f
            )
        }
    }
    fun backMarker(){
        if (noteindex.value > 0) {
            noteindex.value= noteindex.value-1
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(
                    noteList.get(noteindex.value).x.toDouble(),
                    noteList.get(noteindex.value).y.toDouble()
                ), 16.5f
            )
        }
    }
    var maptype= remember {
        mutableStateOf(MapType.NORMAL)
    }
    val listMarkers = listOf(R.drawable.control, R.drawable.inverse_control)
    var imageMarker = remember {
        mutableStateOf(listMarkers[0])
    }
    var lineColorMarker = remember {
        mutableStateOf(Color.Black)
    }

    fun mapTypeChange(){
        if (maptype.value == MapType.NORMAL) {
            maptype.value = MapType.NONE
            imageMarker.value =listMarkers[1]
            lineColorMarker.value = Color.White
        } else {
            maptype.value = MapType.NORMAL
            imageMarker.value = listMarkers[0]
            lineColorMarker.value = Color.Black
        }
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)

    ) {
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .height(93.dp)
                .padding(11.dp)
                .zIndex(3f)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically

        ){
            Icon(
                imageVector = Icons.Outlined.Notes,
                contentDescription = "da",
                modifier = Modifier

                    .weight(0.2f)
                    .size(45.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant

            )
            Text(text = (noteList.size-1).toString()+"/"+noteindex.value.toString(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            Icon(
                imageVector = Icons.Outlined.LinearScale,
                contentDescription = "da",
                modifier = Modifier

                    .weight(0.2f)
                    .size(45.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant

            )
            Text(text = noteDistance.value.toString()+"m",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 90.dp)
                .padding(bottom = 160.dp)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.shapes.medium
                )
                .align(Alignment.Center)
                .zIndex(2f)


        ) {

        }
        Surface(
            modifier= Modifier
                .width(100.dp)
                .height(180.dp)
                .zIndex(2f)
                .align(Alignment.TopEnd)
                .padding(end = 15.dp)
                .padding(top = 95.dp)
                .clip(MaterialTheme.shapes.medium)

                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Image(
                painter = painterResource(id = R.drawable.qr),
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()

                    .align(Alignment.Center),

                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onSecondaryContainer,
                    BlendMode.SrcIn
                )


            )
        }

        Scaffold(
            modifier = Modifier.zIndex(1f),
            snackbarHost = { SnackbarHost(snackbarHostState) },

            bottomBar = {
                Column(horizontalAlignment=Alignment.CenterHorizontally,
                ) {



                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier

                                .height(74.dp)
                                .width(330.dp)
                                .padding(10.dp)
                                .background(
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    MaterialTheme.shapes.medium
                                )
                        ) {
                            var selectedItem by remember { mutableStateOf(2) }
                            val items = listOf("Inicio", "Notas", "Mapa", "Cámara", "Centrar" )
                            val itemsIcon =
                                listOf(
                                    Icons.Filled.Map,
                                    Icons.Filled.ArrowBackIos,
                                    Icons.Filled.Add,
                                    Icons.Filled.ArrowForwardIos,
                                    Icons.Filled.GpsFixed,

                                )
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = { Icon(itemsIcon[index], contentDescription = item) },
                                    label = {  },
                                    alwaysShowLabel = false,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(horizontal = 20.dp),
                                    selected = true,
                                    onClick = {
                                        selectedItem = index
                                        when (index) {
                                            0 -> mapTypeChange()
                                            1 -> backMarker()
                                            2 -> {
                                                var ng = NoteGetter()
                                                ng.set("Nota "+noteList.size,cameraPositionState.position.target.latitude,cameraPositionState.position.target.longitude)
                                                ng.getNotes()

                                            }
                                            3 ->  nextMarker()
                                            4 -> {
                                                cameraPositionState.position = CameraPosition.fromLatLngZoom(ubicaActual.value, 16.5f);
                                                noteindex.value=0
                                            }

                                        }
                                    }
                                )
                            }
                        }


                    var selectedItem by remember { mutableStateOf(2) }
                    val items = listOf("Inicio", "Notas", "Mapa", "Cámara", "Ajustes")
                    val itemsIcon =
                        listOf(
                            Icons.Filled.Home,
                            Icons.Filled.Notes,
                            Icons.Filled.Map,
                            Icons.Filled.Camera,
                            Icons.Filled.Settings,

                        )

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
                                            navController.navigate("Listas")
                                        }
                                        2 -> {
                                            navController.navigate("mapasScreen")
                                        }
                                        3 -> {
                                            selectedItem = 4
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
                    mapType = maptype.value,

                    )



                , googleMapOptionsFactory = {
                    GoogleMapOptions()
                        .backgroundColor(Color.Transparent.hashCode())
                        .compassEnabled(true)
                        .ambientEnabled(true)
                        .useViewLifecycleInFragment(true)

                }


            ) {

                Circle(
                    center = ubicaInicio.value,
                    radius = 40.0,
                    fillColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    strokeColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    zIndex = 1f,
                    strokeWidth = 7f,
                    tag = "circle",
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Ubicación actual")
                        }
                    }
                    )
                for (i in 0..noteList.size - 1) {

                    com.google.maps.android.compose.GroundOverlay(
                        position = GroundOverlayPosition.create(
                            LatLng(noteList.get(i).x.toDouble(), noteList.get(i).y.toDouble()),
                            200f,
                            200f
                        ),
                        image = BitmapDescriptorFactory.fromResource(imageMarker.value),
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
                        onInfoWindowClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Nota: ${noteList.get(i).text}")
                            }
                        },
                        title = noteList.get(i).text,
                        flat = false,
                        zIndex = i.toFloat()
                        //icon = BitmapDescriptorFactory.fromResource(R.drawable.mapafondo),


                    )
                    if(i>0) {
                        Polyline(
                            points = listOf(
                                LatLng(noteList.get(i).x.toDouble(), noteList.get(i).y.toDouble()),
                                LatLng(noteList.get(i-1).x.toDouble(), noteList.get(i-1).y.toDouble())
                            ), color = lineColorMarker.value, width = 5f, zIndex = 6f
                        )
                    }
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