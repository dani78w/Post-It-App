package com.dani.final2.screens

import android.annotation.SuppressLint

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.dani.final2.R
import com.dani.final2.appData.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(navController:NavHostController) {
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
        var context = LocalContext.current
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

                    var selectedItem by remember { mutableStateOf(4) }
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
                        text = "PREMIUM",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 52.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 75.sp
                        ),
                    )
                    Text(
                        text = "Consíguela y desbloquea todas las funciones",
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
                            painter = painterResource(id = R.drawable.diamond),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.inverseSurface,
                                BlendMode.SrcIn
                            )

                        )

                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .height(190.dp)
                            .width(250.dp)


                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .rotate(animaterotation(velocity = 10.dp).value*(5))
                                .fillMaxWidth()
                                .height(30.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.inverseSurface,
                                BlendMode.SrcIn
                            )

                        )

                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .height(190.dp)
                            .width(250.dp)


                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .rotate(animaterotation(velocity = 10.dp).value*(-1))
                                .fillMaxWidth()
                                .height(30.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.inverseSurface,
                                BlendMode.SrcIn
                            )

                        )

                    }


                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .height(190.dp)
                            .width(150.dp)
                            .padding(top = 90.dp,)

                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .rotate(animaterotation(velocity = 10.dp).value)
                                .fillMaxWidth()
                                .height(30.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.inverseSurface,
                                BlendMode.SrcIn
                            )

                        )

                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .height(190.dp)
                            .width(150.dp)
                            .padding(top = 90.dp,)

                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .rotate(animaterotation(velocity = 10.dp).value)
                                .fillMaxWidth()
                                .height(30.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.inverseSurface,
                                BlendMode.SrcIn
                            )

                        )

                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .height(190.dp)
                            .width(150.dp)
                            .padding(top = 90.dp,)

                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .rotate(animaterotation(velocity = 10.dp).value)
                                .fillMaxWidth()
                                .height(30.dp),
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.inverseSurface,
                                BlendMode.SrcIn
                            )

                        )

                    }
                    Button(
                        onClick = {
                            val url = "https://github.com/dani78w"
                            startActivity(context,Intent(Intent.ACTION_VIEW, Uri.parse(url)).setPackage("com.android.chrome"),null)


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
                            text = "Ir a github",
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            fontSize = 15.sp
                        )

                    }

                }

                Spacer(modifier = Modifier.height(30.dp))

                val items = listOf(
                    "Notas en realidad aumentada",
                    "Conexión con facebook",
                    "Login con google y facebook",
                    "Qr para compartir",
                    "Portal web para ver desde el pc"
                )
                val extended = listOf(
                    "Cuando te pasas a la versión premium de nuestra app de notas con realidad aumentada (AR), vas a notar una mejora increíble en cómo tomas apuntes. Vas a poder hacer anotaciones en 3D flotantes y compartir y colaborar en tiempo real con otras personas. ¡Lleva tu productividad al siguiente nivel!",
                    "Al conectar tu cuenta de Facebook en nuestra aplicación, tendrás acceso a una serie de beneficios exclusivos. Podrás compartir fácilmente tus notas y logros con tus amigos, descubrir y unirte a comunidades temáticas, y recibir notificaciones personalizadas sobre eventos y actualizaciones relevantes. ¡Disfruta de una experiencia más social y conectada!",
                    "Al utilizar el inicio de sesión con Google y Facebook en nuestra aplicación, obtendrás ventajas únicas. Podrás acceder rápidamente a tu cuenta con un solo clic, sincronizar tus datos en múltiples dispositivos y disfrutar de una experiencia de usuario fluida. Además, podrás compartir contenido con tus amigos y descubrir nuevas conexiones dentro de la comunidad. ¡Únete y simplifica tu experiencia de inicio de sesión!",
                    "Al escanear el código QR de nuestra aplicación, podrás compartir tus notas con tus amigos y familiares. También podrás acceder a contenido exclusivo, como videos, imágenes y más. ¡Comparte tus notas con el mundo!",
                    "Con nuestro portal web exclusivo para ver desde tu PC, puedes acceder y visualizar tus notas de manera conveniente. Simplemente inicia sesión en tu cuenta desde cualquier navegador web y tendrás acceso completo a tu contenido, permitiéndote leer, editar y organizar tus notas desde la comodidad de tu computadora. ¡Amplía tus posibilidades y disfruta de una experiencia más versátil"

                )
                val itemsIcon =
                    listOf(
                        Icons.Filled.Camera,
                        Icons.Filled.Facebook,
                        Icons.Filled.Login,
                        Icons.Filled.QrCode,
                        Icons.Filled.Computer
                    )


                Column(Modifier.padding(horizontal=20.dp),horizontalAlignment = Alignment.CenterHorizontally){
                    Spacer(modifier = Modifier.height(30.dp))
                    Icon(imageVector = Icons.Filled.KeyboardDoubleArrowUp, contentDescription ="yes" ,modifier = Modifier
                        .size(30.dp)

                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    for(i in 0..itemsIcon.size-1){

                        Row(
                            modifier= Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(MaterialTheme.shapes.medium),




                        ) {
                            Icon(imageVector = itemsIcon[i], contentDescription = "icono",
                                Modifier
                                    .weight(0.2f)
                                    .height(40.dp))
                            Column(
                                modifier = Modifier
                                    .weight(0.8f)
                                    .wrapContentHeight()) {
                                Text(text = items[i],
                                    Modifier
                                        .padding(vertical = 2.dp),fontSize = 15.sp, textAlign = TextAlign.Start)
                                Text(text = extended[i],
                                    Modifier
                                        ,fontSize = 15.sp, textAlign = TextAlign.Start)
                            }

                        }
                        Spacer(modifier = Modifier.height(50.dp))
                    }

                }
                Spacer(modifier = Modifier.height(140.dp))


            }
        }

    }

}