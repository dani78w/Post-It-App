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
import com.dani.final2.createAcount
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Listas(navController: NavHostController) {
    var headerState by rememberSaveable() {
        mutableStateOf(250)
    }

    var ng = NoteGetter()

    val db = Firebase.firestore
    db.collection("notes").addSnapshotListener() { value, error ->
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
    Scaffold(modifier = Modifier.zIndex(1f),
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
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        label = { Text("Nota") },
                        maxLines = 1,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.StickyNote2,
                                contentDescription = "siguiente"
                            )
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .wrapContentHeight()
                            .padding(horizontal = 5.dp)
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
                                val note = hashMapOf(
                                    "email" to "user",
                                    "note" to textInput.text,
                                    "ubi" to lc.value
                                )
                                val db = Firebase.firestore

                                db.collection("notes").document().set(note)
                                textList.add(textInput.text)
                                textInput = TextFieldValue("")
                                headerState = 0
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Nota agregada",
                                        actionLabel = "Ok"
                                    )
                                }


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

                var selectedItem by remember { mutableStateOf(0) }
                val items = listOf("Notas", "Mapa", "Cámara")
                val itemsIcon = listOf(Icons.Filled.Notes, Icons.Filled.Map, Icons.Filled.Camera)
                NavigationBar(modifier = Modifier.height(73.dp)) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(itemsIcon[index], contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when (index) {
                                    1 -> {
                                        navController.navigate("mapasScreen")
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

        floatingActionButton = {
            if (headerState != 0) {
            } else {
                FloatingActionButton(modifier = Modifier
                    .padding(5.dp)
                    .size(57.dp)

                    .background(Color.Transparent, MaterialTheme.shapes.large),
                    onClick = {
                        var ng = NoteGetter()
                        ng.deleteNotes()
                        textList.clear()
                        headerState = 250
                    }) {
                    Icon(
                        imageVector = Icons.Sharp.Delete,
                        contentDescription = "siguiente",
                        modifier = Modifier.size(43.dp)
                    )
                }


            }

        }

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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()

                    .verticalScroll(rememberScrollState())
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


                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.padding(horizontal=15.dp,vertical=3.dp)
                )

                //Si panel de notas detectado ocultar todo
                for (i in noteList.distinct()) {
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
                        if(!i.equals("N")) {
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
                                            noteList.remove(i)
                                        }
                                        .align(Alignment.CenterVertically),
                                    tint = MaterialTheme.colorScheme.inverseSurface
                                )
                            }
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
                                Text(text = i.text, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .height(110.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(
                                            MaterialTheme.colorScheme.surface,
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
                                        text = i.ubi.toString(),
                                        modifier = Modifier
                                            .weight(0.6f)
                                            .padding(10.dp)
                                    )
                                }
                            }

                        }
                    }

                }
                Spacer(modifier = Modifier.height(240.dp))

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
    }


}






