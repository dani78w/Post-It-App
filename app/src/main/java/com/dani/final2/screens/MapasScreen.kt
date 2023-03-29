package com.dani.final2.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.dani.final2.R
import com.dani.final2.appData.NoteGetter
import com.dani.final2.appData.lc
import com.dani.final2.appData.textList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun MapasScreen(navController: NavHostController) {
    Mapas(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mapas(navController: NavHostController) {


    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.zIndex(1f),
        snackbarHost = { SnackbarHost(snackbarHostState) },
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

                var selectedItem by remember { mutableStateOf(1) }
                val items = listOf("Notas", "Mapa", "Cámara")
                val itemsIcon = listOf(Icons.Filled.Notes, Icons.Filled.Map, Icons.Filled.Camera)
                NavigationBar(modifier = Modifier.height(73.dp)) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(itemsIcon[index], contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index
                                when (index) {
                                    0 -> {
                                        navController.navigate("listas")
                                    }
                                    2 -> {
                                        navController.navigate("mapasScreen")

                                    }
                                }
                            }
                        )
                    }
                }
            }
        },
    ) {


        Text(
            text = "Mapas",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(it)
        )


    }
}