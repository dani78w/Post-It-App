package com.dani.final2.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.dani.final2.appData.userName
import com.dani.final2.createAcount

@Composable
fun CreateAcountScreen(navController: NavHostController) {
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
        CreateAcount(navController)
    }

}



@Composable
fun CreateAcount(navController: NavHostController) {


    var paisInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var nombreInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var edadInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

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
                .padding(20.dp)
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
                        painter = painterResource(id = R.drawable.user),
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
                    .padding(horizontal = 10.dp)
                    .scrollable(
                        state = rememberScrollState(),
                        orientation = Orientation.Vertical,
                        true
                    )
                    .animateContentSize(
                        animationSpec = tween(500)

                    ),
            ) {
                Text(
                    text = "Crea tu cuenta", textAlign = TextAlign.Center, modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .animateContentSize(
                            animationSpec = tween(500)
                        ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = TextStyle.Default.copy(
                        fontWeight = FontWeight.W800,
                        fontSize = 24.sp,
                        textDecoration = null,
                        baselineShift = BaselineShift.None
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = nombreInput,
                    onValueChange = { nombreInput = it },
                    label = { Text("Nombre") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
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
                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = paisInput,
                    onValueChange = { paisInput = it },
                    label = { Text("País") },
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Flag,
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
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        textDecoration = null,
                        letterSpacing = 11.sp
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = edadInput,
                    onValueChange = { edadInput = it },
                    label = { Text("Edad") },
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Cake,
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
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        textDecoration = null,
                        letterSpacing = 11.sp
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(47.dp)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)

                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    LinearProgressIndicator(
                        modifier = Modifier
                            .weight(0.1f)
                            .height(47.dp)
                            .align(Alignment.CenterVertically)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                        ,
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                }

                Spacer(
                    modifier = Modifier
                        .weight(0.1f)
                )


            }

            Button(
                onClick = {

                    userName.value = nombreInput.text
                    navController.navigate("Listas")





                }, modifier = Modifier
                    .height(67.dp)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "siguiente",
                    modifier = Modifier.size(43.dp)
                )
            }


            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

        }
    }
}
