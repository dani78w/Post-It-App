package com.dani.final2.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.dani.final2.R
import com.dani.final2.appData.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScren(navController: NavHostController) {

    if(userName.value.isEmpty()){
        userName.value= "Anonimo"
    }
    var context = LocalContext.current


    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {
            // Aquí puedes realizar cualquier tarea asíncrona, como una llamada de red
            // o una consulta a una base de datos, etc.
            // En este ejemplo, simulamos una tarea con delay.
            NoteGetter().getNotes()
            false
        }
        isLoading = result
    }



    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)

    ) {

        Image(
            painter = painterResource(id = R.drawable.fondo7),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .alpha(0.6f),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.primary,
                BlendMode.Modulate
            )


        )

        Scaffold(
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

                    var selectedItem by remember { mutableStateOf(0) }
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
                                            navController.navigate("qrScannerScreen")
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
            Column(
                modifier = Modifier.verticalScroll(
                    state = rememberScrollState(),
                    enabled = true,
                    flingBehavior = ScrollableDefaults.flingBehavior(),
                )
            ) {
                Box(
                    modifier = Modifier

                        .height(600.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxWidth()

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.favs),
                        contentDescription = "Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp)
                            .zIndex(2f)
                            .align(Alignment.Center)
                            .alpha(0.6f),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.primary,
                            BlendMode.Modulate
                        )
                    )



                }

                Box(
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth()
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 500,
                                delayMillis = 100
                            )
                        ),

                    ) {

                    Text(text = session.toUpperCase(), modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 52.dp)
                        , style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 75.sp
                        ),)
                    Text(text = "Sesión actual de tus notas", modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 48.dp)
                        , style = TextStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp
                        ),)
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(top = 30.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mapafondotranslucido),
                            contentDescription = "Logo",


                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.primary,
                                BlendMode.SrcIn
                            ),
                        )

                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(190.dp)
                            .width(250.dp)


                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.session),
                            contentDescription = "Logo",
                            modifier = Modifier

                                .width(1030.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.inverseSurface,
                                BlendMode.SrcIn
                            )

                        )

                    }
                    Button(
                        onClick = {
                            navController.navigate("Sesion")
                        }, modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .width(160.dp)

                            .zIndex(3f)
                            .height(40.dp)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.shapes.small
                            )
                    ) {
                        Text(
                            text = "Cambiar sesión",
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            fontSize = 15.sp
                        )

                    }

                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .animateContentSize(
                            animationSpec = tween(500)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Smartphone,
                        contentDescription = "da",
                        modifier = Modifier

                            .weight(1f)
                            .size(45.dp)

                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(120.dp)


                        .animateContentSize(
                            animationSpec = tween(500)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Row(modifier = Modifier.weight(0.5f), horizontalArrangement = Arrangement.End) {
                        Image(
                            painter = painterResource(id = R.drawable.rightconnector),
                            contentDescription = "Background",
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant),
                            modifier = Modifier

                                .fillMaxHeight()
                                .padding(end = 4.dp)


                        )
                    }
                    Row(modifier = Modifier.weight(0.5f)) {
                        Image(
                            painter = painterResource(id = R.drawable.leftconnector),
                            contentDescription = "Background",
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),

                            modifier = Modifier

                                .fillMaxHeight()
                                .padding(start = 4.dp)


                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

                        .wrapContentHeight()
                        .clip(MaterialTheme.shapes.medium)

                        .animateContentSize(
                            animationSpec = tween(500)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Row(modifier = Modifier.weight(0.2f)) {
                        Icon(
                            imageVector = Icons.Filled.Storage,
                            contentDescription = "da",
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(0.4f)
                                .size(35.dp)
                        )
                        Text(
                            text = "SQLite",
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(top = 15.dp)
                        )
                    }
                    Row(modifier = Modifier.weight(0.2f)) {
                        Icon(
                            imageVector = Icons.Filled.NetworkPing,
                            contentDescription = "da",
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(0.4f)
                                .size(35.dp)
                        )
                        Text(
                            text = "FireBase",
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(top = 15.dp)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(140.dp))


            }
        }

    }
}


