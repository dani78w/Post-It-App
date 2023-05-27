package com.dani.final2.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.dani.final2.R
import com.dani.final2.appData.SessionGetter
import com.dani.final2.appData.userName
import com.google.android.gms.common.api.GoogleApiActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionMScreen(navController: NavHostController) {
    var idSession by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var passwordSession by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
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
                .zIndex(3f)
                .alpha(0.6f),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.primary,
                BlendMode.Modulate
            )


        )
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .zIndex(2f)
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(13.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500,
                            delayMillis = 100
                        )
                    )
            ) {
                Box(modifier = Modifier.height(250.dp)) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.CardMembership, contentDescription = "asd",
                            modifier = Modifier
                                .padding(top = 80.dp)
                                .align(Alignment.CenterHorizontally)
                                .size(100.dp),
                            tint = MaterialTheme.colorScheme.primary,


                            )
                    }


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
                }
                Column() {
                    Spacer(modifier = Modifier.height(50.dp))
                    OutlinedTextField(
                        value = idSession,
                        onValueChange = { idSession = it },
                        label = { Text("Id sesión") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.CardMembership,
                                contentDescription = "siguiente"
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(67.dp)
                            .padding(horizontal = 10.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                        maxLines = 1,
                        textStyle = TextStyle.Default.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            textDecoration = null,
                            baselineShift = BaselineShift.None
                        )
                    )
                    Divider(thickness = 5.dp, color = Color.Transparent)
                    OutlinedTextField(
                        value = passwordSession,
                        onValueChange = { passwordSession = it },
                        label = { Text("Contraseña") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.VpnKey,
                                contentDescription = "siguiente"
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(67.dp)
                            .padding(horizontal = 10.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                        maxLines = 1,
                        visualTransformation = PasswordVisualTransformation(),
                        textStyle = TextStyle.Default.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            textDecoration = null,
                            baselineShift = BaselineShift.None
                        )
                    )
                    Divider(thickness = 5.dp, color = Color.Transparent)

                    Row(
                        modifier = Modifier.padding(horizontal = 7.dp)
                    ) {
                        Row(
                            modifier = Modifier

                                .clickable(onClick = {
                                    GoogleApiActivity().intent
                                })
                                .padding(horizontal = 2.dp)
                                .height(47.dp)
                                .fillMaxHeight()
                                .weight(0.35f)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.medium
                                )

                        ) {


                            Text(
                                text = "  Crear",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .weight(0.7f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 10.dp),
                                color = MaterialTheme.colorScheme.primary,
                                style = TextStyle.Default.copy(
                                    fontWeight = FontWeight.W700,
                                    fontSize = 24.sp,
                                    textDecoration = null,
                                    baselineShift = BaselineShift.None
                                )
                            )
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "siguiente",
                                modifier = Modifier
                                    .weight(0.4f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 0.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                        }
                        Column(
                            verticalArrangement = Arrangement.Center,

                            modifier = Modifier

                                .clickable(onClick = {
                                    GoogleApiActivity().intent
                                })
                                .padding(horizontal = 1.dp)
                                .height(47.dp)
                                .fillMaxHeight()
                                .weight(0.12f)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.medium
                                )

                        ) {

                            Icon(
                                imageVector = Icons.Filled.HideSource,
                                contentDescription = "siguiente",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .width(25.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                        }
                        Row(
                            modifier = Modifier

                                .clickable(onClick = {
                                    GoogleApiActivity().intent
                                })
                                .padding(horizontal = 2.dp)
                                .height(47.dp)
                                .fillMaxHeight()

                                .weight(0.44f)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.medium
                                )
                                .border(
                                    1.dp,
                                    Color(
                                        MaterialTheme.colorScheme.primary.value
                                    ),
                                    MaterialTheme.shapes.medium,

                                    )
                        ) {


                            Text(
                                text = "  Escanear",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .weight(0.7f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 10.dp),
                                color = MaterialTheme.colorScheme.primary,
                                style = TextStyle.Default.copy(
                                    fontWeight = FontWeight.W700,
                                    fontSize = 24.sp,
                                    textDecoration = null,
                                    baselineShift = BaselineShift.None
                                )
                            )
                            Icon(
                                imageVector = Icons.Filled.QrCode,
                                contentDescription = "siguiente",
                                modifier = Modifier
                                    .weight(0.3f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 0.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))



                        Button(
                            onClick = {
                                var sm = SessionGetter()
                                sm.set(idSession.text,passwordSession.text)
                                navController.navigate("Listas")


                            }, modifier = Modifier

                                .size(67.dp)
                                .align(Alignment.CenterHorizontally)


                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "siguiente",
                                modifier = Modifier.size(43.dp)
                            )
                        }


                }
            }
        }
    }
}

