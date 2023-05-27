package com.dani.final2.screens

import android.annotation.SuppressLint

import android.location.GpsSatellite
import android.location.Location
import android.telephony.CarrierConfigManager.Gps
import android.view.MotionEvent
import android.view.MotionEvent.actionToString
import android.widget.EditText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dani.final2.R
import com.dani.final2.appData.*

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.compose.material.icons.sharp.HideSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString


@Composable
fun ListasScreen(navController: NavHostController) {

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
        Listas(navController)
    }

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ServiceCast")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Listas(navController: NavHostController) {

    var headerState by rememberSaveable() {
        mutableStateOf(250)
    }
    var ng = NoteGetter()
    var contexto = LocalContext.current
    val db = Firebase.firestore
    db.collection(session).addSnapshotListener() { value, error ->
        if (error != null) {
            return@addSnapshotListener
        }

        noteList.clear()
        textList.clear()
        ng.getNotes()
        ng.purgeDuplicates()

    }


    var textInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }


    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.zIndex(1f),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(
                modifier = Modifier.wrapContentHeight()
            ) {


                BottomAppBar(
                    containerColor = Color.Transparent,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(7.dp)
                        .background(Color.Transparent, MaterialTheme.shapes.medium)
                ) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        label = { Text(" Crear una nota") },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.StickyNote2,
                                contentDescription = "siguiente"
                            )
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .wrapContentHeight()
                            .padding(start = 5.dp)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.shapes.medium
                            )

                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                        textStyle = TextStyle.Default.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            textDecoration = null,
                        )
                    )

                    if (textInput.text.isNotEmpty()) {
                        OutlinedButton(
                            border = BorderStroke(0.dp, Color.Transparent),
                            shape = MaterialTheme.shapes.large,
                            onClick = {

                                ng.set(textInput.text)

                                textInput = TextFieldValue("")
                                headerState = 0


                            }, modifier = Modifier
                                .height(67.dp)
                                .animateContentSize(
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                                .aspectRatio(1f)
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.shapes.medium
                                ), contentPadding = PaddingValues(0.dp)


                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "siguiente",
                                modifier = Modifier.size(28.dp)

                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }

                var selectedItem by remember { mutableStateOf(1) }
                val items = listOf("Inicio", "Notas", "Mapa", "Cámara", "Ajustes")
                val itemsIcon =
                    listOf(
                        Icons.Filled.Home,
                        Icons.Filled.Notes,
                        Icons.Filled.Map,
                        Icons.Filled.Camera,
                        Icons.Filled.Settings
                    )
                NavigationBar(modifier = Modifier.height(83.dp)) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(itemsIcon[index], contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = {
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
        // Screen content


        Surface() {
            if (noteList.isNotEmpty()) {
                headerState = 0

            } else {
                headerState = 250
            }
            Box(
                modifier = Modifier
                    .height(headerState.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500,
                            delayMillis = 100
                        )
                    ),

                ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mapafondotranslucido),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(top = 30.dp),
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
                        .padding(bottom = 45.dp)

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lapiz),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .width(1030.dp),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.primary,
                            BlendMode.SrcIn
                        )

                    )
                }

            }


        }
        Column(
            modifier = Modifier.padding(top = headerState.dp)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()

                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true,
                        flingBehavior = ScrollableDefaults.flingBehavior(),
                    )

                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.surface)
                    .animateContentSize(
                        animationSpec = tween(200)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentHeight()
                        .clip(MaterialTheme.shapes.medium)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                        .animateContentSize(
                            animationSpec = tween(500)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ViewInAr,
                        contentDescription = "da",
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(0.2f)
                            .size(35.dp)
                    )
                    Text(
                        text = lc.value,
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(10.dp)
                    )
                }
                if (headerState == 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(MaterialTheme.shapes.medium)

                            .animateContentSize(
                                animationSpec = tween(500)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        var colapseButtonText by remember { mutableStateOf("Expand") }
                        var colapseButtonIcon by remember { mutableStateOf(Icons.Outlined.Expand) }
                        Row(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(10.dp, 5.dp, 5.dp, 10.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.medium
                                )
                                .clickable(true) {
                                    if (listState.value) {
                                        listState.value = false
                                        colapseButtonText = "Colapse"
                                        colapseButtonIcon = Icons.Outlined.Compress
                                    } else {
                                        listState.value = true
                                        colapseButtonText = "Expand"
                                        colapseButtonIcon = Icons.Outlined.Expand
                                    }
                                    val clipboardManager =
                                        contexto.getSystemService(ClipboardManager::class.java)
                                    clipboardManager?.setText(AnnotatedString("EEEpA"))

                                }
                        ) {


                            Text(
                                text = colapseButtonText,
                                modifier = Modifier
                                    .weight(0.6f)
                                    .padding(10.dp)
                                    .align(Alignment.CenterVertically)

                            )
                            Icon(
                                imageVector = colapseButtonIcon,
                                contentDescription = "da",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .weight(0.4f)
                                    .size(25.dp)
                            )

                        }

                        Row(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(5.dp, 5.dp, 10.dp, 10.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.medium

                                )
                                .clickable {
                                    var ng = NoteGetter()
                                    ng.deleteNotes()
                                    textList.clear()
                                    headerState = 250
                                }
                        ) {
                            Text(
                                text = "Borrar todo",
                                modifier = Modifier
                                    .weight(0.7f)
                                    .padding(10.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Icon(
                                imageVector = Icons.Sharp.Delete,
                                contentDescription = "da",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .weight(0.3f)
                                    .size(25.dp)

                            )

                        }


                    }
                }

                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.padding(horizontal = 175.dp, vertical = 22.dp)
                )

                //Si panel de notas detectado ocultar todo


                ng.showNotes(scope, snackbarHostState,navController)

                /*
                if (!(noteList.isEmpty())) {
                    var tamImage = remember {
                        mutableStateOf(90.dp)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .wrapContentHeight()
                            .animateContentSize(
                                animationSpec = tween(500)
                            ),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storage,
                            contentDescription = "",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Notas locales",
                            fontSize = 32.sp,
                            color = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = "SQLite",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )

                    }

                    for (i in 0..noteList.size-1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(10.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .zIndex(2f)

                                .animateContentSize(

                                    animationSpec = tween(1000),
                                )
                        ) {
                        Spacer(modifier = Modifier.height(150.dp).zIndex(0f))
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
                            var icons = remember {
                                mutableStateOf(Icons.Outlined.CheckBoxOutlineBlank)
                            }

                            Icon(
                                imageVector = icons.value,
                                contentDescription = "",
                                modifier = Modifier

                                    .zIndex(3f)
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(bottom = 0.dp)
                                    .padding(start = 20.dp)
                                    .align(Alignment.BottomStart)
                                    .clickable {
                                        if (icons.value == Icons.Outlined.CheckBoxOutlineBlank) {
                                            icons.value = Icons.Outlined.CheckBox
                                        } else {
                                            icons.value = Icons.Outlined.CheckBoxOutlineBlank
                                        }
                                    }
                                    .animateContentSize(

                                        animationSpec = tween(1000),
                                    )
                                    .alpha(1f),
                            )
                            Icon(
                                imageVector = Icons.Filled.Square,
                                contentDescription = "",
                                modifier = Modifier

                                    .zIndex(2f)
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(bottom = 0.dp)
                                    .padding(start = 20.dp)
                                    .align(Alignment.BottomStart)

                                    .animateContentSize(

                                        animationSpec = tween(1000),
                                    )
                                    .alpha(1f),
                                    tint=Color(MaterialTheme.colorScheme.secondaryContainer.value)
                            )


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(vertical = 20.dp)
                                    .padding(start = 40.dp)
                                    .padding(bottom = 10.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .zIndex(1f)
                                    .align(Alignment.BottomEnd)
                                    .animateContentSize(
                                        animationSpec = tween(1000),
                                    )
                            ) {
                                Column() {
                                    Row(
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .fillMaxWidth()
                                    ) {

                                        Text(
                                            text = "Nota", fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .padding(2.dp)
                                                .padding(start = 55.dp)
                                                .weight(0.4f),
                                        )

                                        Icon(imageVector = Icons.Sharp.HideSource,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clickable {

                                                }
                                                .align(Alignment.CenterVertically),
                                            tint = MaterialTheme.colorScheme.inverseSurface
                                        )

                                        Icon(imageVector = Icons.Default.AddLocation,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(4.dp)

                                                .align(Alignment.CenterVertically),
                                            tint = MaterialTheme.colorScheme.inverseSurface
                                        )

                                        Icon(imageVector = Icons.Default.Delete,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(4.dp)

                                                .align(Alignment.CenterVertically),
                                            tint = MaterialTheme.colorScheme.inverseSurface
                                        )
                                    }


                                Text(
                                        text= noteList[i].text,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .padding(start = 64.dp)
                                )
                                }
                            }


                        }

                    }

                }
                */

                Spacer(modifier = Modifier.height(240.dp))
            }


        }
        /*Generador de notas puras sin gps
            for (i in textList.distinct()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentHeight()
                        .clip(MaterialTheme.shapes.medium)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                        .animateContentSize(
                            animationSpec = tween(500)
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "NOTA",
                            modifier = Modifier
                                .padding(start = 9.dp)
                                .align(Alignment.CenterVertically)
                                .weight(0.4f),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )
                        Icon(imageVector = Icons.Default.AddLocation,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {

                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            withDismissAction = true,
                                            message = "Aún no está implementado"
                                        )
                                    }
                                }
                                .align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                        Icon(imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    textList.remove(i)
                                }
                                .align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                    }
                    Box(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant)

                    ) {
                        Spacer(modifier = Modifier.height(200.dp))
                        Column() {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = i, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                    }
                }

            }
            Spacer(modifier = Modifier.height(240.dp))
            */


    }

}










