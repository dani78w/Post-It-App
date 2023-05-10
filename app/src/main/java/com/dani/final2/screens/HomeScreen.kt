package com.dani.final2.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dani.final2.appData.lc
import com.dani.final2.appData.lcd
import com.dani.final2.appData.textList
import com.dani.final2.appData.userName
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScren(navController: NavHostController) {
    Box(modifier = Modifier.padding(0.dp)) {

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
        ){
            val drawerState = rememberDrawerState(DrawerValue.Closed)
        }
    }
}