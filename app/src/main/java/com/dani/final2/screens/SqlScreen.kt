package com.dani.final2.screens

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dani.final2.R
import com.dani.final2.appData.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SqlScreen(navController: NavHostController) {
    var Contexto = navController.context
    var Lng = LocalNoteGetter(Contexto)
    Lng.sqlCreateNote(Contexto, LocalNote("hola", 0.0, 0.0))
    Lng.showAllNotes()

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
        var textInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        Scaffold(
            bottomBar = {
                Column(
                ) {


                    var selectedItem by remember { mutableStateOf(0) }
                    val items = listOf("Inicio", "Notas", "Mapa", "Cámara", "Ajustes")
                    val itemsIcon = listOf(
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
                                            navController.navigate("CameraScreen")
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
            var tamImage = remember {
                mutableStateOf(90.dp)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState(), enabled = true, flingBehavior = null)
            ) {
                for (i in 0..8) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(10.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .zIndex(2f)
                            .padding(10.dp)
                            .animateContentSize(

                                animationSpec = tween(1000),
                            )
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.texture),
                            contentDescription = "Background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .zIndex(2f)
                                .height(tamImage.value)
                                .width(tamImage.value)
                                .align(Alignment.TopStart)
                                .clickable {
                                    if (tamImage.value == 300.dp) {
                                        tamImage.value = 90.dp
                                    } else {
                                        tamImage.value = 300.dp
                                    }
                                }
                                .animateContentSize(

                                    animationSpec = tween(1000),
                                )
                                .alpha(1f),
                        )


                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 20.dp)
                                .padding(start = 40.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .zIndex(1f)
                                .align(Alignment.BottomEnd)
                                .animateContentSize(
                                    animationSpec = tween(1000),
                                )
                        ) {
                            Column() {
                                Text(
                                    text = "Nota", fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier=Modifier
                                        .padding(2.dp)
                                        .padding(start=55.dp)

                                )
                                Text(text = "HOASDJ FOADF GPASDBFIHSABDFPHISADF FIUPBPSDAIFBASDUB FDSAYB FPSADSDFVA",
                                    modifier=Modifier
                                        .padding(2.dp)
                                        .padding(start=64.dp))
                            }


                        }

                    }

                }

            }

        }
    }

}