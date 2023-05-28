package com.dani.final2

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Start() {
    Scaffold(


    ) {
        Surface() {
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        //.offset(x = animateMovement(velocity = 100.dp).value.dp)
                        .width( animatewidth(velocity = 100.dp).value.dp)
                        .height( animatewidth(velocity = 100.dp).value.dp)
                        .background(Color.Red)
                        .align(Alignment.Center)
                ) {

                }
            }
        }

        }
    }



@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED, showBackground = true)
@Composable
fun StartPreview(   ) {
    Start()
}
@Composable
fun animateMovement(velocity: Dp): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetX = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = with(LocalDensity.current) { velocity.toPx() },
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    return offsetX
}

@Composable
fun animatewidth(velocity: Dp): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()

    val width = infiniteTransition.animateFloat(
        initialValue = 400f,
        targetValue = 450f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    return width
}


