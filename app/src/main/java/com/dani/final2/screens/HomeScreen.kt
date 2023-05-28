package com.dani.final2.screens

import android.annotation.SuppressLint

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableDefaults

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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.*

import androidx.compose.ui.zIndex

import androidx.navigation.NavHostController
import com.dani.final2.R

import com.dani.final2.appData.*

import kotlinx.coroutines.*

import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScren(navController: NavHostController) {

    if (userName.value.isEmpty()) {
        userName.value = "Anonimo"
    }
    var context = LocalContext.current


    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {

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

        var totaltam by remember { mutableStateOf(0) }
        var tam by remember { mutableStateOf(0) }
        var tam2 by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {


            totaltam = 400
            tam = 120
            tam2 = 40
        }
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
                    val items = listOf("Inicio", "Notas", "Mapa",  "Exportar","Premium")
                    val itemsIcon =
                        listOf(
                            Icons.Filled.Hexagon,
                            Icons.Filled.StickyNote2,
                            Icons.Filled.Map,
                            Icons.Filled.IosShare,
                            Icons.Filled.Diamond

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
                                            selectedItem = 0
                                            navController.navigate("homeScreen")
                                        }
                                        1 -> {
                                            selectedItem = 1
                                            navController.navigate("listas")
                                        }
                                        2 -> {
                                            selectedItem = 2
                                            navController.navigate("mapasScreen")
                                        }
                                        3 -> {
                                            selectedItem = 3
                                            NoteGetter().shareNoteList(context)
                                        }
                                        4 -> {
                                            selectedItem = 4
                                            navController.navigate("premiumScreen")
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

                        .height(350.dp)
                        .fillMaxWidth()
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 1500,

                                )
                        ),

                    ) {

                    Text(
                        text = session.toUpperCase(),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 52.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 75.sp
                        ),
                    )
                    Text(
                        text = "Sesión actual de tus notas",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 48.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp
                        ),
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(top = animatesize(velocity = 100.dp).value.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mapafondotranslucido),
                            contentDescription = "Logo",
                            alpha = animatealpha(velocity = 5.dp).value,

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
                                .rotate(animaterotation(velocity = 10.dp).value)
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

                Column(
                    modifier = Modifier
                        .height(totaltam.dp)
                        .animateContentSize(
                            animationSpec = tween(1500)
                        ),
                ) {


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
                            .height(tam.dp)

                            .animateContentSize(
                                animationSpec = tween(1500)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Row(
                            modifier = Modifier.weight(0.5f),
                            horizontalArrangement = Arrangement.End
                        ) {
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



                }




            }
        }

    }
}

@Composable
fun animatesize(velocity: Dp): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()

    val size = infiniteTransition.animateFloat(
        initialValue = 0.dp.value,
        targetValue = 40.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                easing = CubicBezierEasing(0.42f, 0f, 0.58f, 0.7f)
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    return size
}

@Composable
fun animatealpha(velocity: Dp): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()
    val size = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                easing = CubicBezierEasing(0.42f, 0f, 0.58f, 0.7f)
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    return size
}

@Composable
fun animaterotation(velocity: Dp): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()

    val size = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 8900,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    return size
}



