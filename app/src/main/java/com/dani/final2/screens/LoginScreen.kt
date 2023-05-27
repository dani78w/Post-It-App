package com.dani.final2.screens



import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dani.final2.R

import com.dani.final2.appData.userName
import com.dani.final2.appData.userPass

import com.dani.final2.navigation.AppNavigation
import com.dani.final2.navigation.AppScreens
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiActivity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {


    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.Transparent)
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
        LoginPanel(navController)
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPanel(navController: NavHostController) {

    var passwordInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var userInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {


        Surface(
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
            ) {
                Box(modifier = Modifier.height(350.dp)) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mapafondotranslucido),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .padding(top = 100.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.primary,
                                BlendMode.SrcIn
                            ),
                        )
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(330.dp)
                            .width(350.dp)
                            .padding(bottom = 105.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.locked),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .padding(top = 100.dp)
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
                        .animateContentSize(
                            animationSpec = tween(500)
                        ),
                ) {
                    Text(
                        text = "Inicia sesión", textAlign = TextAlign.Center, modifier = Modifier
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
                        value = userInput,
                        onValueChange = { userInput = it },
                        label = { Text("User") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
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
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        label = { Text("Password") },
                        maxLines = 1,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Security,
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
                        visualTransformation = PasswordVisualTransformation(),
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
                        Row(
                            modifier = Modifier

                                .align(Alignment.CenterVertically)
                                .clickable(onClick = {
                                    GoogleApiActivity().intent
                                })
                                .weight(0.5f)
                                .fillMaxHeight()
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.medium
                                )
                        ) {
                            Spacer(modifier = Modifier.weight(0.1f))
                            Image(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .height(20.dp)
                                    .aspectRatio(1f),
                                colorFilter = ColorFilter.tint(
                                    MaterialTheme.colorScheme.surfaceTint,
                                    BlendMode.SrcIn
                                ),
                            )
                            Text(
                                text = "Google",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 10.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = TextStyle.Default.copy(
                                    fontWeight = FontWeight.W800,
                                    fontSize = 24.sp,
                                    textDecoration = null,
                                    baselineShift = BaselineShift.None
                                )
                            )

                        }
                        Spacer(modifier = Modifier.width(5.dp))

                        Row(
                            modifier = Modifier
                                .clickable(onClick = { /*TODO*/ })
                                .align(Alignment.CenterVertically)
                                .weight(0.5f)
                                .fillMaxHeight()
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.shapes.medium
                                )
                        ) {
                            Spacer(modifier = Modifier.weight(0.1f))
                            Image(
                                painter = painterResource(id = R.drawable.facebook),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .height(20.dp)
                                    .aspectRatio(1f),
                                colorFilter = ColorFilter.tint(
                                    MaterialTheme.colorScheme.surfaceTint,
                                    BlendMode.SrcIn
                                ),
                            )
                            Text(
                                text = "Facebook",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 10.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = TextStyle.Default.copy(
                                    fontWeight = FontWeight.W800,
                                    fontSize = 24.sp,
                                    textDecoration = null,
                                    baselineShift = BaselineShift.None
                                )
                            )

                        }
                        Spacer(modifier = Modifier.width(10.dp))

                    }

                    Spacer(
                        modifier = Modifier
                            .weight(0.1f)
                    )


                }
                var context = LocalContext.current
                Button(
                    onClick = {


                        userName.value = userInput.text

                    try {
                        createAcount(navController,userInput.text, passwordInput.text)
                    }catch (e:Exception) {
                        singInAnonimously()
                        Toast.makeText(
                            context,
                            "Error al crear la cuenta , se iniciara sesion anonima",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate("HomeScreen")
                    }

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

}





private fun createAcount(navController: NavController,email: String, password: String):Boolean {
    var output = false
    val db = Firebase.firestore
    var auth = FirebaseAuth.getInstance()
    val user = hashMapOf(
        "email" to email,
        "password" to password,
    )
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isComplete) {
                print("Cuenta creada")
                output=!output
                userName.value=email.toString().split("@")[0]
                navController.navigate("CreateAcountScreen")
            }
            if(task.isCanceled){
                logIn(navController,email, password)
            }
        }
    db.collection("users").document().set(user)
    userPass.value=true
    return output
}
private fun logIn(navController: NavController,email: String, password: String){
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isComplete) {
                userName.value=email.toString().split("@")[0]
                navController.navigate("HomeScreen")
            }
            if (task.isCanceled) {
                throw Exception("Error al iniciar sesion")
            }
        }
}

private fun singInAnonimously(){

    var auth= FirebaseAuth.getInstance()
    auth.signInAnonymously()
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                userName.value="Anonimo"
            } else {
                print("Error al crear la cuenta")
            }
        }
}
